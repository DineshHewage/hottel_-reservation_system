package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDetails {
    private int customer_id;
    private String name;
    private String email;
    private String phone;
    private String address;
}
