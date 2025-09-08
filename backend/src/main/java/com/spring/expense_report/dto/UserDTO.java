
package com.spring.expense_report.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Bangkok")
    private java.util.Date createdAt;
    private RoleDTO role;

    public UserDTO() {
        super();
    }

    public UserDTO(Long id, String name, String email, java.util.Date createdAt, Long roleId, String roleName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.role = new RoleDTO(roleId, roleName);
    }

}