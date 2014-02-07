package model.beans;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author skuarch
 */
@Entity
@Table(name = "scheduler")
@NamedQueries(
        @NamedQuery(
                name = "getSchedulerByName",
                query = "from Scheduler as s where s.name = :name"))
public class Scheduler implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "scheduler_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "scheduler_name", nullable = false)
    private String name;
    @Column(name = "scheduler_period")
    private short period;
    @Column(name = "scheduler_status")
    private boolean status;
    @OneToMany(mappedBy = "scheduler", cascade = CascadeType.ALL)
    private Set<NetworkNode> nodes;

    //==========================================================================
    public Scheduler() {
    }

    //==========================================================================
    public Scheduler(String name, short period, boolean status) {
        this.name = name;
        this.period = period;
        this.status = status;        
    } // end Scheduler

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getPeriod() {
        return period;
    }

    public void setPeriod(short period) {
        this.period = period;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<NetworkNode> getNodes() {
        return nodes;
    }

    public void setNodes(Set<NetworkNode> nodes) {
        this.nodes = nodes;
    }

    /*@Override
    public String toString() {
        return "id=" + id + " name=" + name + " period=" + period + " status=" + status + " nodes=" + nodes;
    }*/
    
    

} // end class
