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

    @Id
    @Column(name = "scheduler_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "scheduler_name", nullable = false)
    private String name;
    @Column(name = "scheduler_period")
    private int period;
    @Column(name = "scheduler_running")
    private boolean running;    
    @OneToMany(mappedBy = "scheduler",cascade = CascadeType.ALL)    
    private Set<NetworkNode> nodes;

    public Scheduler() {
    }

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

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Set<NetworkNode> getNodes() {
        return nodes;
    }

    public void setNodes(Set<NetworkNode> nodes) {
        this.nodes = nodes;
    }

} // end class
