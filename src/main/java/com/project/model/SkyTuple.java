package com.project.model;

import java.io.Serializable;

public class SkyTuple implements Serializable {
	
	private long timeStampId;
	
	private boolean remove;
	
	private long expiration;
	
	private int dimensions;
	
	private double[] values;

	private boolean isMin;

	private long election;
	
	private int layer;

	public SkyTuple() {}

	public SkyTuple(long timeStampId, long expiration, int dimensions, double[] values) {
		setTimeStampId(timeStampId);
		setExpiration(expiration);
		setDimensions(dimensions);		
		setValues(values);
		setAsMinimum(false);
		setRemove(false);
	}

	// getters
	
	public long getTimeStampId() {
		return timeStampId;
	}
	
	public boolean getRemove() {
		return remove;
	}
	
	public long getExpiration() {
		return expiration;
	}

	public int getDimensions() {
		return dimensions;
	}

	public double[] getValues() {
		return values;
	}

	public boolean getIsMinimum() {
		return isMin;
	}

	public long getElection() {
		return election;
	}

	public int getLayer() {
		return layer;
	}
	
	// setters
	
	public void setTimeStampId(long timeStampId) {
		this.timeStampId = timeStampId;
	}
	
	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

	public void setDimensions(int dimensions) {
		this.dimensions = dimensions;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

	public void setAsMinimum(boolean isMin) {
		this.isMin = isMin;
	}

	public void setElection(long election) {
		this.election = election;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}
/*
	@Override
	public String toString() {		
		String asJson = "{" +
				"\"timeStampId\": \"" + timeStampId + "\"," +
				"\"remove\": \"" + remove + "\"," +
				"\"expiration\": \"" + expiration + "\"," +
				"\"dimensions\": \"" + dimensions + "\"," +
				"\"isMin\": \"" + isMin + "\"," +
				"\"election\": \"" + election + "\"," +
				"\"layer\": \"" + layer + "\"," +
				"\"values\": \"[";
		
		int i = 0;
		for(; i < values.length - 1; i++) {
			asJson += values[i] + ",";
		}

		asJson += values[i] + "]\"}";
		
		return asJson;
	}
*/
}
