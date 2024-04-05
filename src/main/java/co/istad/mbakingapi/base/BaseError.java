package co.istad.mbakingapi.base;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseError<T> {
    //Request Entity to large , Bad Request,...
    // 7000 - 7001:email, 7003
    private String code;
    //details error for user
    private T description;

}