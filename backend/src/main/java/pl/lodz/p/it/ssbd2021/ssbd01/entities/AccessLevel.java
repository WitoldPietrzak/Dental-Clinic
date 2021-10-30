package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * Typ Access level reprezentujący poziom dostępu konta aplikacji.
 */
@Entity
@Table(name = "access_levels", uniqueConstraints = {
        @UniqueConstraint(name = "acc_lvl_level_account_pair_unique", columnNames = {"level", "account_id"})})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "level", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
        @NamedQuery(name = "AccessLevel.findAll", query = "SELECT a FROM AccessLevel a"),
        @NamedQuery(name = "AccessLevel.findById", query = "SELECT a FROM AccessLevel a WHERE a.id = :id"),
        @NamedQuery(name = "AccessLevel.findByLevel", query = "SELECT a FROM AccessLevel a WHERE a.level = :level"),
        @NamedQuery(name = "AccessLevel.findByActive", query = "SELECT a FROM AccessLevel a WHERE a.active = :active"),
        @NamedQuery(name = "AccessLevel.findByAccountId", query = "SELECT a FROM AccessLevel a WHERE a.accountId.id = :accountId"),
        @NamedQuery(name = "AccessLevel.findByAccountIdAndAccessLevel", query = "SELECT a FROM AccessLevel a WHERE a.accountId.id = :accountId AND a.level = :level"),
        @NamedQuery(name = "AccessLevel.findByAccountLogin", query = "SELECT a FROM AccessLevel a WHERE a.accountId.login = :accountLogin"),
        @NamedQuery(name = "AccessLevel.findByAccountLoginAndAccessLevel", query = "SELECT a FROM AccessLevel a WHERE a.accountId.login = :accountLogin AND a.level = :level"),
        @NamedQuery(name = "AccessLevel.findByVersion", query = "SELECT a FROM AccessLevel a WHERE a.version = :version"),
        @NamedQuery(name = "AccessLevel.findByCreationDateTime", query = "SELECT a FROM AccessLevel a WHERE a.creationDateTime = :creationDateTime"),
        @NamedQuery(name = "AccessLevel.findByModificationDateTime", query = "SELECT a FROM AccessLevel a WHERE a.modificationDateTime = :modificationDateTime")})
public class AccessLevel extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    //@NotNull
    private Long id;

    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active = true;

    @Basic(optional = false)
    @Column(name = "level", nullable = false, length = 32, updatable = false, insertable = false)
    @Size(min = 7, max = 32)
    private String level;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "account_id", referencedColumnName = "id", updatable = false)
    @NotNull
    private Account accountId;

    /**
     * Tworzy nową instancję klasy Access level.
     */
    public AccessLevel() {
    }

    /**
     * Tworzy nową instancję klasy AccessLevel.
     *
     * @param level   nazwa poziomu dostepu
     * @param account status
     */
    public AccessLevel(String level, Account account) {
        this.level = level;
        this.accountId = account;
    }

    /**
     * Tworzy nową instancję klasy Access level.
     *
     * @param account account
     * @param active  active
     */
    public AccessLevel(Account account, Boolean active) {
        this.accountId = account;
        this.active = active;
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Pobiera pole level.
     *
     * @return level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Pobiera pole active.
     *
     * @return active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Pobiera pole accountId.
     *
     * @return accountId
     */
    public Account getAccountId() {
        return accountId;
    }

    /**
     * Ustawia pole active.
     *
     * @param active active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Ustawia pole account id.
     *
     * @param accountId account id
     */
    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd01.entities.AccessLevel[ id=" + id + " ]";
    }

}
