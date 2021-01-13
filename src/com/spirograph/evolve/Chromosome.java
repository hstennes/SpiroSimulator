package com.spirograph.evolve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.spirograph.design.SpiroInfo;

public class Chromosome {

	private final int valuesInGear = 4;
	private final int minPercent = 50;
	private final int maxPercent = 150;
	private float[] values;
	private Random random;
	
	public Chromosome(SpiroInfo spiroInfo) {
		values = new float[spiroInfo.getInfo().size() * valuesInGear];
		random = new Random();
		encode(spiroInfo);
	}
	
	private void encode(SpiroInfo spiroInfo) {
		for(int i = 0; i < spiroInfo.getInfo().size(); i++) {
			String currentInfo  = spiroInfo.getGearInfo(i);
			for(int j = 0; j < valuesInGear; j++) {
				float value = 0;
				switch(j) {
				case 0:
					value = spiroInfo.getRadius(currentInfo);
					break;
				case 1:
					value = spiroInfo.getDotRadius(currentInfo);
					break;
				case 2:
					value = spiroInfo.getSpeed(currentInfo);
					break;
				case 3:
					value = booleanToFloat(spiroInfo.getPlacementType(currentInfo));
					break;
				default:
					System.out.println("Ye dun goofed");
					break;
				}
				values[i * valuesInGear + j] = value;
			}
		}
	}
	
	public SpiroInfo decode() {
		ArrayList<String> info = new ArrayList<String>();
		printValues();
		for(int i = 0; i < values.length / valuesInGear; i++) {
			String gear = "";
			for(int j = 0; j < valuesInGear; j++) {
				if(j == 1) gear = gear + (int) values[i * valuesInGear + j] + ",";
				else if(j != 3) gear = gear + values[i * valuesInGear + j] + ",";
				else gear = gear + floatToBoolean(values[i * valuesInGear + j]);
			}
			info.add(gear);
			System.out.println(gear);
		}
		
		return new SpiroInfo(info);
	}
	
	private float booleanToFloat(boolean b) {
		if(b) return 1;
		return 0;
	}
	
	private boolean floatToBoolean(float f) {
		if(f == 1) return true;
		else return false;
	}
	
	public void crossover(Chromosome x) {
		System.out.println("starting crossover__________________________________________________");
		float[] values2 = x.getValues();
		System.out.println("Parent 1: ");
		printValues();
		System.out.println("Parent 2: ");
		x.printValues();
		int crossover;
		crossover = 1 + random.nextInt(Math.min(values.length, values2.length) - 1);
		System.out.println("Crossover: " + crossover);
		float[] subarray1 = Arrays.copyOfRange(values, crossover, values.length);
		float[] subarray1b = Arrays.copyOfRange(values, 0, crossover);
		float[] subarray2 = Arrays.copyOfRange(values2, crossover, values2.length);
		float[] subarray2b = Arrays.copyOfRange(values2, 0, crossover);
		float[] newValues = concat(subarray1b, subarray2);
		float[] newValues2 = concat(subarray2b, subarray1);
		setValues(verify(newValues));
		System.out.println("Offspring 1: ");
		printValues();
		x.setValues(verify(newValues2));
		System.out.println("Offspring 2: ");
		x.printValues();
		
	}
	
	private float[] verify(float[] original) {
		if(original[original.length - 3] == -1) {
			original[original.length - 3] = random.nextInt((int) original[original.length - 4]);
		}
		for(int i = 0; i < original.length - 4; i++) {
			int type = i % valuesInGear;
			if(type == 1) original[i] = -1;
		}
		return original;
	}
	
	public void mutate(float chance) {
		System.out.println("Mutation method");
		for(int i = 0; i <  values.length; i++) {
			int type = i % valuesInGear;
			if(random.nextDouble() < chance) {
				System.out.println("Mutating index: " + i + " with type: " + type);
				if(type == 3) values[i] = 1 - values[i];
				else if(values[i] != -1) {
					values[i] = values[i] * (minPercent + random.nextInt(maxPercent - minPercent)) / 100;
				}
			}
		}
	}
	
	private float[] concat(float[] a, float[] b) {
		float[] result = new float[a.length + b.length];
		for(int i = 0; i < result.length; i++) {
			if(i < a.length) result[i] = a[i];
			else result[i] = b[i - a.length];
		}
		return result;
	}
	
	public void printValues() {
		for(int i = 0; i < values.length; i++) {
			if(i % 4 == 0) System.out.print(" | " + values[i] + ", ");
			else System.out.print(values[i] + ", ");
		}
		System.out.println();
	}
	
	public float[] getValues() {
		return values;
	}
	
	public void setValues(float[] values) {
		this.values = values;
	}
}
