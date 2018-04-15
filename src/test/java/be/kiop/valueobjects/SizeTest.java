package be.kiop.valueobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;

import org.junit.Test;

import be.kiop.exceptions.IllegalFactorException;
import be.kiop.exceptions.IllegalSizeException;
import be.kiop.exceptions.NegativeHeightException;
import be.kiop.exceptions.NegativeWidthException;

public class SizeTest {
	@Test
	public void Size_positiveWidthAndHeight_sizeCreated() {
		assertNotNull(new Size(1,1));
	}
	
	@Test(expected = NegativeWidthException.class)
	public void Size_negativeWidthAndPositiveHeight_exception() {
		new Size(-1,1);
	}
	
	@Test(expected = NegativeHeightException.class)
	public void Size_positiveWidthAndNegativeHeight_exception() {
		new Size(1,-1);
	}
	
	@Test
	public void sum_bothSizesAreNotNull_sumOfSizes() {
		Size s1 = new Size(1,1);
		Size s2 = new Size(2, 2);
		assertEquals(new Size(3,3), Size.sum(s1, s2));
	}
	
	@Test(expected = IllegalSizeException.class)
	public void sum_size1IsNullAndSize2IsNotNull_exception() {
		Size s2 = new Size(2, 2);
		Size.sum(null, s2);
	}
	
	@Test(expected = IllegalSizeException.class)
	public void sum_size1IsNotNullAndSize2IsNull_exception() {
		Size s1 = new Size(1,1);
		Size.sum(s1, null);
	}
	
	@Test
	public void product_sizeIsNotNullAndFactorsArePositive_productOfSizeTimesValues() {
		Size s1 = new Size(1,1);
		assertEquals(new Size(2,3), Size.product(s1, 2, 3));
	}
	
	@Test(expected = IllegalSizeException.class)
	public void product_sizeIsNullAndFactorsArePositive_exception() {
		Size.product(null, 1, 1);
	}
	
	@Test(expected = IllegalFactorException.class)
	public void product_sizeIsNotNullAndFactor1IsNegativeAndFactor2IsPositive_exception() {
		Size s1 = new Size(1,1);
		Size.product(s1, -1, 0);
	}
	
	@Test(expected = IllegalFactorException.class)
	public void product_sizeIsNotNullAndFactor1IsPositiveAndFactor2IsNegative_exception() {
		Size s1 = new Size(1,1);
		Size.product(s1, 0, -1);
	}
	
	@Test
	public void getCenter_nA_sizeCenter() {
		Size s1 = new Size(2,2);
		assertEquals(new Position(1,1), s1.getCenter());
	}
	
	@Test
	public void toDimension_nA_coordinatesAsDimension() {
		Size s1 = new Size(2,2);
		assertEquals(new Dimension(2,2), s1.toDimension());
	}
	
	@Test
	public void hashCode_twoSameSize_sameHashCode() {
		Size s1 = new Size(1,1);
		Size s2 = new Size(1,1);
		assertEquals(s1.hashCode(), s2.hashCode());
	}
	
	@Test
	public void equals_sameObject_true() {
		Size s1 = new Size(1,1);
		assertTrue(s1.equals(s1));
	}
	
	@Test
	public void equals_toNull_false() {
		Size s1 = new Size(1,1);
		assertFalse(s1.equals(null));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void equals_differentClass_false() {
		Size s1 = new Size(1,1);
		assertFalse(s1.equals(new Dimension()));
	}
	
	@Test
	public void equals_differentWidth_false() {
		Size s1 = new Size(1,1);
		Size s2 = new Size(2,1);
		assertFalse(s1.equals(s2));
	}
	
	@Test
	public void equals_differentHeight_false() {
		Size s1 = new Size(1,1);
		Size s2 = new Size(1,2);
		assertFalse(s1.equals(s2));
	}
	
	@Test
	public void equals_sameWidthAndHeight_true() {
		Size s1 = new Size(1,1);
		Size s2 = new Size(1,1);
		assertTrue(s1.equals(s2));
	}
}
