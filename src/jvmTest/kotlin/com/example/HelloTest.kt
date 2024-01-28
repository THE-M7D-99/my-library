package com.example

import com.toofan.soft.qsb.api.repo.LoginRepo
import org.junit.jupiter.api.Test
import kotlin.test.assertContains

class LoginRepoTest {
    @Test
    fun testExecute() {
        assertContains(LoginRepo.execute("abcde@gmail.com", "abcde123456789").toString(), "")
//        assertContains(hello.sayHello(), "Hello Ali")
    }
}