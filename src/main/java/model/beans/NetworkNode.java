package model.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author skuarch
 */
@Entity
@Table(name = "network_node")
@NamedQueries(
        {
            @NamedQuery(
                    name = "getNetworkNodeByHost",
                    query = "from NetworkNode as n where n.host = :host"
            ),
            @NamedQuery(
                    name = "getNetworkNodeBySchedulerId",
                    query = "from NetworkNode as n where n.scheduler.id = :schedulerId"
            )
        }
)
public class NetworkNode implements Serializable {

    @Id
    @Column(name = "network_node_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "network_node_host")
    private String host;
    @Column(name = "network_node_timeout")
    private int timeout;
    @ManyToOne
    @JoinColumn(name = "scheduler_id")
    private Scheduler scheduler;

    //==========================================================================
    public NetworkNode() {
    } // NetworkNode

    //==========================================================================
    public NetworkNode(String host, int timeout, Scheduler scheduler) {
        this.host = host;
        this.timeout = timeout;
        this.scheduler = scheduler;
    } // NetworkNode

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

} // end class
