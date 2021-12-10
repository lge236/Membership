package testProject.membership.member.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDTO {

    private String id;
    private String password;
    private String auth;
    private String name;
}
