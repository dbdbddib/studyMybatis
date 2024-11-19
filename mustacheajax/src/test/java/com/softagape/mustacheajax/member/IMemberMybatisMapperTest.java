package com.softagape.mustacheajax.member;

import com.softagape.mustacheajax.commons.dto.CUDInfoDto;
import com.softagape.mustacheajax.member.MemberDto;
import com.softagape.mustacheajax.member.IMember;
import com.softagape.mustacheajax.member.IMemberMybatisMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IMemberMybatisMapperTest {
    @Autowired
    private IMemberMybatisMapper memberMybatisMapper;

    private void AssertFields(IMember left, IMember right) {
        assertThat(left).isNotNull();
        assertThat(right).isNotNull();
        assertThat(left.getId()).isEqualTo(right.getId());
        assertThat(left.getName()).isEqualTo(right.getName());
        assertThat(left.getLoginId()).isEqualTo(right.getLoginId());
//        assertThat(left.getPassword()).isEqualTo(right.getPassword());
// 암호 테스트는 ByCript Bean 생성 못해서 테스트 못함.
        assertThat(left.getNickname()).isEqualTo(right.getNickname());
        assertThat(left.getEmail()).isEqualTo(right.getEmail());
        assertThat(left.getRole()).isEqualTo(right.getRole());
        assertThat(left.getActive()).isEqualTo(right.getActive());
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
    public void MemberInsertTest() {
        CUDInfoDto cudInfoDto = new CUDInfoDto(MemberDto.builder().nickname("junitest").build());
        MemberDto insert = MemberDto.builder().build();
        Throwable exception = assertThrows(Exception.class, () -> {
            memberMybatisMapper.insert(insert);
        });
        log.error("Exception : {}", exception.toString());

        MemberDto insert2 = MemberDto.builder()
                .name("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890").build();
        exception = assertThrows(Exception.class, () -> {
            memberMybatisMapper.insert(insert2);
        });
        log.error("Exception : {}", exception.toString());

        MemberDto insert3 = MemberDto.builder()
                .name("myname")
                .nickname("mynickname")
                .loginId("myloginId")
                .password("12345678")
                .email("myemail@email.com")
                .role(MemberRole.USER.toString())
                .active(false)
                .build();
        cudInfoDto.setCreateInfo(insert3);
        this.memberMybatisMapper.insert(insert3);
        assertThat(insert3).isNotNull();
        assertThat(insert3.getId()).isGreaterThan(0L);
        assertThat(insert3.getName()).isEqualTo("myname");
    }

    @Test
    @Order(2)
    public void MemberFindTest() {
        MemberDto find1 = this.memberMybatisMapper.findById(0L);
        assertThat(find1).isNull();

        MemberDto find2 = this.memberMybatisMapper.findByNickname("mynickname");

        MemberDto find3 = this.memberMybatisMapper.findById(find2.getId());

        this.AssertFields(find3, find2);
    }

    @Test
    @Order(3)
    public void MemberUpdateTest() {
        MemberDto find2 = this.memberMybatisMapper.findByNickname("mynickname");

        find2.setName("ABCDEFGH");
        this.memberMybatisMapper.update(find2);

        MemberDto find3 = this.memberMybatisMapper.findById(find2.getId());

        this.AssertFields(find3, find2);
    }

    @Test
    @Order(4)
    public void MemberDeleteTest() {
        MemberDto find2 = this.memberMybatisMapper.findByNickname("mynickname");
        assertThat(find2).isNotNull();

        this.memberMybatisMapper.deleteById(find2.getId());

        MemberDto find3 = this.memberMybatisMapper.findById(find2.getId());
        assertThat(find3).isNull();
    }
}
