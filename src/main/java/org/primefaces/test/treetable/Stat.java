package org.primefaces.test.treetable;

import java.io.Serializable;

public class Stat implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer period;

    private Integer tpm; // three pointers made
    private Integer ftm; // free throws made
    private Integer fta; // free throws attempted
    private Integer pts; // points

    public Stat(Integer period, Integer tpm, Integer ftm, Integer fta, Integer pts)
    {
        this.period = period;
        
        this.tpm = tpm;
        this.ftm = ftm;
        this.fta = fta;
        this.pts = pts;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
    {
        this.period = period;
    }

    public Integer getTpm()
    {
        return tpm;
    }

    public void setTpm(Integer tpm)
    {
        this.tpm = tpm;
    }

    public Integer getFtm()
    {
        return ftm;
    }

    public void setFtm(Integer ftm)
    {
        this.ftm = ftm;
    }

    public Integer getFta()
    {
        return fta;
    }

    public void setFta(Integer fta)
    {
        this.fta = fta;
    }

    public Integer getPts()
    {
        return pts;
    }

    public void setPts(Integer pts)
    {
        this.pts = pts;
    }

    @Override
    public String toString()
    {
        return "Stat [period=" + period + ", tpm=" + tpm + ", ftm=" + ftm + ", fta=" + fta + ", pts=" + pts + "]";
    }
}
