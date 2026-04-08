package com.nucleus.herdmanager.domain;

import com.nucleus.herdmanager.domain.enumeration.CropType;
import com.nucleus.herdmanager.domain.enumeration.RainfallPattern;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Seed.
 */
@Entity
@Table(name = "seed")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Seed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "crop_type", nullable = false)
    private CropType cropType;

    @NotNull
    @Size(max = 100)
    @Column(name = "region", length = 100, nullable = false)
    private String region;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rainfall_pattern", nullable = false)
    private RainfallPattern rainfallPattern;

    @NotNull
    @Min(value = 0)
    @Column(name = "water_requirement_mm_to_establish", nullable = false)
    private Integer waterRequirementMmToEstablish;

    @Size(max = 500)
    @Column(name = "notes", length = 500)
    private String notes;

    public Long getId() {
        return this.id;
    }

    public Seed id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Seed name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CropType getCropType() {
        return this.cropType;
    }

    public Seed cropType(CropType cropType) {
        this.setCropType(cropType);
        return this;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public String getRegion() {
        return this.region;
    }

    public Seed region(String region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public RainfallPattern getRainfallPattern() {
        return this.rainfallPattern;
    }

    public Seed rainfallPattern(RainfallPattern rainfallPattern) {
        this.setRainfallPattern(rainfallPattern);
        return this;
    }

    public void setRainfallPattern(RainfallPattern rainfallPattern) {
        this.rainfallPattern = rainfallPattern;
    }

    public Integer getWaterRequirementMmToEstablish() {
        return this.waterRequirementMmToEstablish;
    }

    public Seed waterRequirementMmToEstablish(Integer waterRequirementMmToEstablish) {
        this.setWaterRequirementMmToEstablish(waterRequirementMmToEstablish);
        return this;
    }

    public void setWaterRequirementMmToEstablish(Integer waterRequirementMmToEstablish) {
        this.waterRequirementMmToEstablish = waterRequirementMmToEstablish;
    }

    public String getNotes() {
        return this.notes;
    }

    public Seed notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seed)) {
            return false;
        }
        return id != null && id.equals(((Seed) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Seed{" +
            "id=" +
            getId() +
            ", name='" +
            getName() +
            "'" +
            ", cropType='" +
            getCropType() +
            "'" +
            ", region='" +
            getRegion() +
            "'" +
            ", rainfallPattern='" +
            getRainfallPattern() +
            "'" +
            ", waterRequirementMmToEstablish=" +
            getWaterRequirementMmToEstablish() +
            ", notes='" +
            getNotes() +
            "'" +
            "}"
        );
    }
}
