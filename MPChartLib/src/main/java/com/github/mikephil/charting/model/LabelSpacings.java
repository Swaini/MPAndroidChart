package com.github.mikephil.charting.model;

public class LabelSpacings
{
    public float paddingHorizontal = 5f;
    public float paddingVertical = 4f;
    public float marginBottom = 12f;

    public LabelSpacings()
    {
        // Empty
    }

    public LabelSpacings(float paddingHorizontal, float paddingVertical, float marginBottom)
    {
        this.paddingHorizontal = paddingHorizontal;
        this.paddingVertical = paddingVertical;
        this.marginBottom = marginBottom;
    }
}