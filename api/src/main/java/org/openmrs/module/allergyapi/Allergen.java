/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.allergyapi;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;

/**
 * Represent allergen
 */
public class Allergen {
	
	private AllergenType allergenType;
	
	private Concept codedAllergen;
	
	private String nonCodedAllergen;
	
	/**
	 * Default constructor
	 */
	public Allergen(){
	}
	
	/**
	 * @param allergenType the allergenType to set
	 * @param codedAllergen the codedAllergen to set
	 * @param nonCodedAllergen the nonCodedAllergen to set
	 */
	public Allergen(AllergenType allergenType, Concept codedAllergen, String nonCodedAllergen) {
		this.allergenType = allergenType;
		this.codedAllergen = codedAllergen;
		this.nonCodedAllergen = nonCodedAllergen;
	}
	
	/**
	 * @return Returns the allergenType
	 */
	public AllergenType getAllergenType() {
		return allergenType;
	}
	
	/**
	 * @param allergenType the allergenType to set
	 */
	public void setAllergenType(AllergenType allergenType) {
		this.allergenType = allergenType;
	}
	
    /**
     * @return the codedAllergen
     */
    public Concept getCodedAllergen() {
    	return codedAllergen;
    }

    /**
     * @param codedAllergen the codedAllergen to set
     */
    public void setCodedAllergen(Concept codedAllergen) {
		this.codedAllergen = codedAllergen;
		String otherNonCoded = Context.getAdministrationService().getGlobalProperty("concept.otherNonCoded");
		if (codedAllergen != null && codedAllergen.getUuid() != Context.getConceptService().getConcept(otherNonCoded).getUuid()) {
			nonCodedAllergen = null;
		}
    }
	
    /**
     * @return the nonCodedAllergen
     */
    public String getNonCodedAllergen() {
    	return nonCodedAllergen;
    }

    /**
     * @param nonCodedAllergen the nonCodedAllergen to set
     */
    public void setNonCodedAllergen(String nonCodedAllergen) {
		this.nonCodedAllergen = nonCodedAllergen;
		if (StringUtils.isNotBlank(nonCodedAllergen)) {
			String otherNonCoded = Context.getAdministrationService().getGlobalProperty("concept.otherNonCoded");
			codedAllergen = Context.getConceptService().getConcept(otherNonCoded);
		}
    }

	boolean isCoded(){
		String otherNonCoded = Context.getAdministrationService().getGlobalProperty("concept.otherNonCoded");
		if (codedAllergen == null || codedAllergen.getUuid() == Context.getConceptService().getConcept(otherNonCoded).getUuid()) {
			return false;
		}
		return true;
	}

	@Override
    public String toString() {
	    if (StringUtils.isNotBlank(nonCodedAllergen)) {
	    	return nonCodedAllergen;
	    }
	    return codedAllergen.getName().getName();
    }
}
