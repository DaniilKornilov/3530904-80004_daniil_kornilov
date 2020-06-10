package main.entity;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name = "department_id", nullable = false)
    private Integer depId;

    @Column(name = "date_beg", nullable = false)
    private Calendar dateBeg;

    @Column(name = "date_end", nullable = false)
    private Calendar dateEnd;

    @Column(name = "date_end_real", nullable = false)
    private Calendar dateEndReal;

    public Project() {
    }

    public Project(String name, double cost, int depId, Calendar dateBeg, Calendar dateEnd, Calendar dateEndReal) {
        this.name = name;
        this.cost = cost;
        this.depId = depId;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
        this.dateEndReal = dateEndReal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public String getDateBeg() {
        String month, day;
        if (this.dateBeg.get(Calendar.MONTH) < 9) {
            month = "0" + (this.dateBeg.get(Calendar.MONTH) + 1);
        } else {
            month = (this.dateBeg.get(Calendar.MONTH) + 1) + "";
        }
        if (this.dateBeg.get(Calendar.DAY_OF_MONTH) < 10) {
            day = "0" + dateBeg.get(Calendar.DAY_OF_MONTH);
        } else {
            day = dateBeg.get(Calendar.DAY_OF_MONTH) + "";
        }
        return this.dateBeg.get(Calendar.YEAR) + "/" + month + "/" + day;
    }

    public void setDateBeg(Calendar dateBeg) {
        this.dateBeg = dateBeg;
    }

    public String getDateEnd() {
        String month, day;
        if (this.dateEnd.get(Calendar.MONTH) < 9) {
            month = "0" + (this.dateEnd.get(Calendar.MONTH) + 1);
        } else {
            month = (this.dateEnd.get(Calendar.MONTH) + 1) + "";
        }
        if (this.dateEnd.get(Calendar.DAY_OF_MONTH) < 10) {
            day = "0" + dateEnd.get(Calendar.DAY_OF_MONTH);
        } else {
            day = dateEnd.get(Calendar.DAY_OF_MONTH) + "";
        }
        return this.dateEnd.get(Calendar.YEAR) + "/" + month + "/" + day;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDateEndReal() {
        String month, day;
        if (this.dateEndReal.get(Calendar.MONTH) < 9) {
            month = "0" + (this.dateEndReal.get(Calendar.MONTH) + 1);
        } else {
            month = (this.dateEndReal.get(Calendar.MONTH) + 1) + "";
        }
        if (this.dateEndReal.get(Calendar.DAY_OF_MONTH) < 10) {
            day = "0" + dateEndReal.get(Calendar.DAY_OF_MONTH);
        } else {
            day = dateEndReal.get(Calendar.DAY_OF_MONTH) + "";
        }
        return this.dateEndReal.get(Calendar.YEAR) + "/" + month + "/" + day;
    }

    public void setDateEndReal(Calendar dateEndReal) {
        this.dateEndReal = dateEndReal;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", depId=" + depId +
                ", dateBeg=" + dateBeg +
                ", dateEnd=" + dateEnd +
                ", dateEndReal=" + dateEndReal +
                '}';
    }
}
