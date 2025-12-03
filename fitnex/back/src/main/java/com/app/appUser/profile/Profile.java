package com.app.appUser.profile;

import com.app.enums.AgeCategory;
import com.app.enums.Gender;
import com.app.util.Global;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Profile implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "profile_g")
    @SequenceGenerator(name = "profile_g", sequenceName = "profile_seq", allocationSize = 1)
    private Long id;

    private String fio = "ФИО";
    private String tel = "Телефон";

    private String date = Global.getDateNow();

    private String educate = "Образование";
    private String speciality = "Специализация";

    @Column(length = 1000)
    private String img = "/img/avatar.png";

    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.MAN;
    @Enumerated(EnumType.STRING)
    private AgeCategory ageCategory = AgeCategory.AC18_20;

    public Profile(String fio, String tel, String date, String educate, String speciality) {
        this.fio = fio;
        this.tel = tel;
        this.date = date;
        this.educate = educate;
        this.speciality = speciality;
    }

    public void update(Profile update) {
        this.fio = update.getFio();
        this.tel = update.getTel();
        this.date = update.getDate();
        this.educate = update.getEducate();
        this.speciality = update.getSpeciality();
    }

}