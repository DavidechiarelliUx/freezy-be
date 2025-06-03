package freezy.freezy_be.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
