package com.abdullah.repository.entity;

import com.abdullah.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user_profile")
public class UserProfile extends BaseEntity{

    @Id//spring frameworkten gelicek id
    private String id;
    private Long authId;
    private String username;
    private String email;
    private String phone;
    private String avatar;
    private String adress;
    private String about;


    @Builder.Default
    private EStatus eStatus=EStatus.PENDING;

}
