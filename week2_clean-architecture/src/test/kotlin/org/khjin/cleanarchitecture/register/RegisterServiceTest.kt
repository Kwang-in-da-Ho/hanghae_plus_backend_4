package org.khjin.cleanarchitecture.register

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.khjin.cleanarchitecture.exception.*
import org.khjin.cleanarchitecture.lecture.LectureEntity
import org.khjin.cleanarchitecture.lecture.LectureRepository
import org.khjin.cleanarchitecture.register.dto.CheckRegistrationRequest
import org.khjin.cleanarchitecture.register.dto.RegisterRequest
import org.khjin.cleanarchitecture.register.dto.RegistrationStatus
import org.khjin.cleanarchitecture.user.UserEntity
import org.khjin.cleanarchitecture.user.UserRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class RegisterServiceTest {

    //set test object and data
    companion object {
        private val stubRegisterRepo = TestRegisterReopository()
        private val stubUserRepo = TestUserRepository()
        private val stubLectureRepo = TestLectureRepository()
        private val sut: RegisterService = RegisterService(stubRegisterRepo, stubUserRepo, stubLectureRepo)

        private val testUser = UserEntity(0L, "testUser")
        private val testLecture = LectureEntity(
            0L
            , "test Lecture"
            , LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.NOON) //yesterday noon
            , 30)
        private val notOpenLecture = LectureEntity(
            1L
            , "test Lecture - Registration Not Open"
            , LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON) //tomorrow noon
            , 30 )

        @JvmStatic
        @BeforeAll
        fun setTestData() {
            stubUserRepo.save(testUser)
            stubLectureRepo.save(testLecture)
            stubLectureRepo.save(notOpenLecture)
        }

        @JvmStatic
        @AfterAll
        fun clearTestData() {
            stubUserRepo.deleteById(testUser.userId)
            stubLectureRepo.deleteById(testLecture.lectureId)
            stubLectureRepo.deleteById(notOpenLecture.lectureId)
        }
    }

    @AfterEach
    fun clearRegisterData(){
        stubRegisterRepo.deleteAll()
    }

    @Test
    @DisplayName("존재하지 않는 사용자id로 수강신청 시도 시 InvalidUserException 발생")
    fun `when a user attempts to register with an invalid user id, throw InvalidUserException`() {
        //given
        sut.register(RegisterRequest(testUser.userId, testLecture.lectureId))
        val invalidUserId = 9999L

        assertThrows<InvalidUserException> {
            sut.register(RegisterRequest(invalidUserId, testLecture.lectureId))
        }
    }

    @Test
    @DisplayName("존재하지 않는 강의id로 수강신청 시도 시 InvalidLectureException 발생")
    fun `when a user attempts to register with an invalid lecture id, InvalidLectureException should be thrown`() {
        //given
        sut.register(RegisterRequest(testUser.userId, testLecture.lectureId))
        val invalidLectureId = 9999L

        assertThrows<InvalidLectureException> {
            sut.register(RegisterRequest(testUser.userId, invalidLectureId))
        }
    }

    @Test
    @DisplayName("이미 등록한 사용자가 또 다시 등록을 시도한다면 DuplicateRegistrationException 발생")
    fun `when a user who already registered for a lecture attempts to register again, DuplicateRegistrationException should be thrown`() {
        //given
        sut.register(RegisterRequest(testUser.userId, testLecture.lectureId))

        assertThrows<DuplicateRegistrationException> {
            sut.register(RegisterRequest(testUser.userId, testLecture.lectureId))
        }
    }

    @Test
    @DisplayName("아직 수강신청이 열리지 않은 특강에 대해 수강신청을 하면 LectureRegistrationNotOpenException 발생")
    fun `when a user attempts to register for a lecture that has not opened, LectureRegistrationNotOpenException should be thrown`() {
        assertThrows<LectureRegistrationNotOpenException> {
            sut.register(RegisterRequest(testUser.userId, notOpenLecture.lectureId))
        }
    }

    @Test
    @DisplayName("수강 인원이 다 찬 강의에 수강신청할 경우 StudentsFullException 발생")
    fun `when a user attempts to register for a full lecture, StudentsFullException should be thrown`() {
        //given
        val capacity = testLecture.studentCapacity
        //insert user and register data until capacity is filled
        (10..<10+capacity).forEach { i ->
            stubUserRepo.save(UserEntity(i.toLong(), "test user $i"))
            sut.register(RegisterRequest(i.toLong(), testLecture.lectureId))
        }

        assertThrows<StudentsFullException> {
            sut.register(RegisterRequest(testUser.userId, testLecture.lectureId))
        }
    }

    @Test
    @DisplayName("존재하지 않는 사용자id로 수강신청내역 조회 시 InvalidUserException 발생")
    fun `when a user queries for a registration data with invalid user id, InvalidUserException should be thrown`() {
        //given
        val invalidUserId = 9999L

        assertThrows<InvalidUserException> {
            sut.checkRegistration(CheckRegistrationRequest(invalidUserId, testLecture.lectureId))
        }
    }

    @Test
    @DisplayName("존재하지 않는 사용자id로 수강신청내역 조회 시 InvalidUserException 발생")
    fun `when a user queries for a registration data with invalid lecture id, InvalidLectureException should be thrown`() {
        //given
        val invalidLectureId = 9999L

        assertThrows<InvalidLectureException> {
            sut.checkRegistration(CheckRegistrationRequest(testUser.userId, invalidLectureId))
        }
    }

    @Test
    @DisplayName("신청 완료 여부를 조회하였을 때 등록자 명단에 있다면 RegistrationStatus.SUCCESS가 반환된다")
    fun `when a is registered for a lecture, RegistrationStatus-SUCCESS should be returned`() {
        //given
        sut.register(RegisterRequest(testUser.userId, testLecture.lectureId))

        val result = sut.checkRegistration(CheckRegistrationRequest(testUser.userId, testLecture.lectureId))
        assertEquals(result.registrationStatus, RegistrationStatus.SUCCESS)
    }

    @Test
    @DisplayName("신청 완료 여부를 조회하였을 때 등록자 명단에 없다면 RegistrationStatus.FAIL이 반환된다")
    fun `when a user is not registered for a lecture, RegistrationStatus-FAIL should be returned`() {
        val result = sut.checkRegistration(CheckRegistrationRequest(testUser.userId, testLecture.lectureId))
        assertEquals(result.registrationStatus, RegistrationStatus.FAIL)
    }

    //Repository stubs
    private class TestRegisterReopository: RegisterRepository{

        private val tempTable = mutableMapOf<String, RegisterEntity>()
        private val keyDelim="-"

        override fun save(user: UserEntity, lecture: LectureEntity): RegisterEntity {
            val register = RegisterEntity(user, lecture, LocalDateTime.now())
            tempTable.put(generateId(user.userId, lecture.lectureId), register)
            return register
        }

        override fun findById(user: UserEntity, lecture: LectureEntity): RegisterEntity? {
            return tempTable.get(generateId(user.userId, lecture.lectureId))
        }

        override fun deleteById(user: UserEntity, lecture: LectureEntity) {
            tempTable.remove(generateId(user.userId, lecture.lectureId))
        }

        override fun findByLecture(lecture: LectureEntity): List<RegisterEntity> {
            val result = mutableListOf<RegisterEntity>()
            tempTable.forEach { key, reg ->
                if( key.split(keyDelim)[1].toLong() == lecture.lectureId ) result.add(reg)
            }
            return result
        }

        fun deleteAll() {
            tempTable.clear()
        }

        private fun generateId(userId: Long, lectureId: Long): String{
            return "$userId$keyDelim$lectureId"
        }
    }

    private class TestUserRepository: UserRepository{
        private val tempTable = mutableMapOf<Long, UserEntity>()
        override fun findById(userId: Long): UserEntity? {
            return tempTable.get(userId)
        }

        override fun save(user: UserEntity): UserEntity {
            tempTable.put(user.userId, user)
            return user
        }

        override fun deleteById(userId: Long) {
            tempTable.remove(userId)
        }
    }

    private class TestLectureRepository: LectureRepository{
        private val tempTable = mutableMapOf<Long, LectureEntity>()
        override fun findById(lectureId: Long): LectureEntity? {
            return tempTable.get(lectureId)
        }

        override fun save(lecture: LectureEntity): LectureEntity {
            tempTable.put(lecture.lectureId, lecture)
            return lecture
        }

        override fun deleteById(lectureId: Long) {
            tempTable.remove(lectureId)
        }

    }
}