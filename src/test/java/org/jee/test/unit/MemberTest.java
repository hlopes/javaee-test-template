package org.jee.test.unit;

import static org.junit.Assert.assertNotNull;

import org.jee.model.Member;
import org.junit.Test;

/**
 * Class for an example of unit test.
 * 
 * @author Hugo
 */
public class MemberTest {

    @Test
    public void addMemberTest() {
        Member newMember = new Member();
        newMember.setName("Jane Doe");
        newMember.setEmail("jane@mailinator.com");
        newMember.setPhoneNumber("2125551234");

        assertNotNull(newMember);
    }
}
