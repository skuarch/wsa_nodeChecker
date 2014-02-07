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

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "network_node_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "network_node_host")
    private String host;
    @Column(name = "network_node_timeout")
    private short timeout;    
    @Column(name = "network_node_trigger_alarm")
    private short triggerAlarm;    
    @ManyToOne
    @JoinColumn(name = "scheduler_id")
    private Scheduler scheduler;

    //==========================================================================
    public NetworkNode() {
    } // NetworkNode

    //==========================================================================
    public NetworkNode(String host, short timeout, Scheduler scheduler,short triggerAlarm) {
        this.host = host;
        this.timeout = timeout;
        this.scheduler = scheduler;
        this.triggerAlarm = triggerAlarm;
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

    public short getTimeout() {
        return timeout;
    }

    public void setTimeout(short timeout) {
        this.timeout = timeout;
    }

    public short getTriggerAlarm() {
        return triggerAlarm;
    }

    public void setTriggerAlarm(short triggerAlarm) {
        this.triggerAlarm = triggerAlarm;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

} // end class
