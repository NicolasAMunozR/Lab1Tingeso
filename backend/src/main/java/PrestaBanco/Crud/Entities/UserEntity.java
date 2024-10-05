package PrestaBanco.Crud.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identifying_document")
    private String identifyingDocument;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Lob
    private byte[] file;

    public UserEntity(String identifyingDocument, String name, String email, String password, byte[] file) {
        this.identifyingDocument = identifyingDocument;
        this.name = name;
        this.email = email;
        this.password = password;
        this.file = file;
    }
}