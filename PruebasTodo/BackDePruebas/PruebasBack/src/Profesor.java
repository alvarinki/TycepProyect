
public class Profesor extends Usuario{

//    @Column(name = "usuario_id", nullable = false)
//    private Integer id;
//
//    @MapsId
//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "usuario_id", nullable = false)
//    private Usuario usuario;

    @Column(name = "dni", length = 20)
    private String dni;

    @Column(name = "correo")
    private String correo;

//    @OneToMany(mappedBy = "idProfesor", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<ProfesorCurso> profesorCursos = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="Profesor_Curso", joinColumns =
            {@JoinColumn(name = "id_profesor")},
            inverseJoinColumns = {@JoinColumn(name = "id_curso")})
    @JsonIgnore
    private Set<Curso> cursos= new LinkedHashSet<>();

    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="profesor_asignatura", joinColumns =
            {@JoinColumn(name="id_profesor")},
            inverseJoinColumns = {@JoinColumn(name = "id_asignatura")})
    private Set<Asignatura> asignaturas= new LinkedHashSet<>();
}