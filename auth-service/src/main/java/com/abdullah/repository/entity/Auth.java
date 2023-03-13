package com.abdullah.repository.entity;

import com.abdullah.repository.enums.ERole;
import com.abdullah.repository.enums.EStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
/*soru
    1-Repo
    2-Service
    3-Controller
    -register methodu olacak ve buna bir endpoint yazılacak bu islemler bir request dto ile yapilacak
    donus tipide bir dto olsun yine auth kendisini donmeyelim response dto donelim
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Auth extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String activationCode;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ERole role=ERole.USER;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EStatus status=EStatus.PENDING;
}
