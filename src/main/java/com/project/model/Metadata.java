package com.project.model;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "predicate_id" }) )
public class Metadata extends BaseEntity {

    @ManyToOne(cascade = { DETACH, REFRESH, MERGE }, optional = false)
    private FieldPredicate predicate;

    @Column(nullable = false)
    private Boolean repeatable;

    @Column(nullable = false)
    private Boolean required;

    @ManyToMany(cascade = { DETACH, REFRESH, MERGE }, fetch = LAZY)
    private List<FieldGloss> fieldGlosses;

    @ManyToMany(cascade = { DETACH, REFRESH, MERGE }, fetch = LAZY)
    private List<ControlledVocabulary> controlledVocabularies;
 
    public Metadata() {
    	fieldGlosses = new ArrayList<FieldGloss>();
    	controlledVocabularies = new ArrayList<ControlledVocabulary>(); 
    }

	public FieldPredicate getPredicate() {
		return predicate;
	}

	public void setPredicate(FieldPredicate predicate) {
		this.predicate = predicate;
	}

	public Boolean getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(Boolean repeatable) {
		this.repeatable = repeatable;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public List<FieldGloss> getFieldGlosses() {
		return fieldGlosses;
	}

	public void setFieldGlosses(List<FieldGloss> fieldGlosses) {
		this.fieldGlosses = fieldGlosses;
	}
	
	public void addFieldGloss(FieldGloss fieldGloss) {
		fieldGlosses.add(fieldGloss);
	}
	
	public void removeFieldGloss(FieldGloss fieldGloss) {
		fieldGlosses.remove(fieldGloss);
	}
	
	public void clearFieldGlosss() {
		fieldGlosses.clear();
	}

	public List<ControlledVocabulary> getControlledVocabularies() {
		return controlledVocabularies;
	}

	public void setControlledVocabularies(List<ControlledVocabulary> controlledVocabularies) {
		this.controlledVocabularies = controlledVocabularies;
	}
	
	public void addControlledVocabulary(ControlledVocabulary controlledVocabulary) {
		controlledVocabularies.add(controlledVocabulary);
	}
	
	public void removeControlledVocabulary(ControlledVocabulary controlledVocabulary) {
		controlledVocabularies.remove(controlledVocabulary);
	}
	
	public void clearControlledVocabularys() {
		controlledVocabularies.clear();
	}
    
}

