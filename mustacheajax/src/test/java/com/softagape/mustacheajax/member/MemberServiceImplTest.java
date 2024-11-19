package com.softagape.mustacheajax.member;

import com.softagape.mustacheajax.commons.dto.CUDInfoDto;
import com.softagape.mustacheajax.commons.dto.IBase;
import com.softagape.mustacheajax.commons.exeption.IdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberServiceImplTest {
    @Autowired
    private IMemberService memberService;

    private void AssertFields(IMember left, IMember right) {
        assertThat(left).isNotNull();
        assertThat(right).isNotNull();
        assertThat(left.getId()).isEqualTo(right.getId());
        assertThat(left.getName()).isEqualTo(right.getName());
        assertThat(left.getLoginId()).isEqualTo(right.getLoginId());
//        assertThat(left.getPassword()).isEqualTo(right.getPassword());
        assertThat(left.getNickname()).isEqualTo(right.getNickname());
        assertThat(left.getEmail()).isEqualTo(right.getEmail());
        assertThat(left.getRole()).isEqualTo(right.getRole());
        assertThat(left.getActive()).isEqualTo(right.getActive());
    }

    private void AssertBaseFields(IBase left, IBase right) {
        assertThat(left.getCreateDt()).isEqualTo(right.getCreateDt());
        assertThat(left.getCreateId()).isEqualTo(right.getCreateId());
        assertThat(left.getUpdateDt()).isEqualTo(right.getUpdateDt());
        assertThat(left.getUpdateId()).isEqualTo(right.getUpdateId());
        assertThat(left.getDeleteDt()).isEqualTo(right.getDeleteDt());
        assertThat(left.getDeleteId()).isEqualTo(right.getDeleteId());
        assertThat(left.getDeleteFlag()).isEqualTo(right.getDeleteFlag());
    }

    @Test
    @Order(1)
    public void MemberInsertTest() throws Exception {
        CUDInfoDto cudInfoDto = new CUDInfoDto(MemberDto.builder().nickname("junitest").build());
        MemberDto insert = MemberDto.builder().build();
        IMember resultInsert2 = memberService.insert(cudInfoDto, insert);
        assertThat(resultInsert2).isNull();

        MemberDto insert2 = MemberDto.builder()
                .name("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890")
                .nickname("mynicknameS")
                .loginId("myloginIdS")
                .password("12345678")
                .email("myemailS@email.com")
                .role(MemberRole.USER.toString())
                .active(false)
                .build();
        Throwable exception = assertThrows(Exception.class, () -> {
            memberService.insert(cudInfoDto, insert2);
        });
        log.error("Exception : {}", exception.toString());

        insert2.setName("mynameS");
        IMember resultInsert = memberService.insert(cudInfoDto, insert2);
        assertThat(resultInsert).isNotNull();
        assertThat(resultInsert.getId()).isGreaterThan(0L);
        assertThat(resultInsert.getName()).isEqualTo("mynameS");
    }

    @Test
    @Order(2)
    public void MemberFindTest() {
        IMember find1 = this.memberService.findById(0L);
        IMember find2IMember = this.memberService.findByNickname("mynicknameS");
        IMember find3IMember = this.memberService.findById(find2IMember.getId());
        this.AssertFields(find2IMember, find3IMember);
        this.AssertBaseFields(find2IMember, find3IMember);
    }

    @Test
    @Order(3)
    public void MemberUpdateTest() throws Exception {
        CUDInfoDto cudInfoDto = new CUDInfoDto(MemberDto.builder().nickname("junitest").build());
        IMember find2IMember = this.memberService.findByNickname("mynicknameS");
        find2IMember.setName("mynameS222");
        this.memberService.update(cudInfoDto, find2IMember);
        IMember find3IMember = this.memberService.findById(find2IMember.getId());
        this.AssertFields(find2IMember, find3IMember);
    }

    @Test
    @Order(4)
    public void MemberDeleteTest() throws Exception {
        CUDInfoDto cudInfoDto = new CUDInfoDto(MemberDto.builder().nickname("junitest").build());
        IMember find2IMember = this.memberService.findByNickname("mynicknameS");
        assertThat(find2IMember).isNotNull();
        find2IMember.setDeleteFlag(true);
        this.memberService.updateDeleteFlag(cudInfoDto, find2IMember);
        assertThat(find2IMember).isNotNull();
        assertThat(find2IMember.getDeleteFlag()).isEqualTo(true);
        Throwable exception = assertThrows(IdNotFoundException.class, () -> {
            memberService.findById(find2IMember.getId());
        });
        log.error("IdNotFoundException : {}", exception.toString());

        exception = assertThrows(IdNotFoundException.class, () -> {
            memberService.deleteById(find2IMember.getId());
        });
        log.error("IdNotFoundException : {}", exception.toString());
    }
}
