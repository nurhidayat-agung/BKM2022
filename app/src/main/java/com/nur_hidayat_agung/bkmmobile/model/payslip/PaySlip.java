package com.nur_hidayat_agung.bkmmobile.model.payslip;

import android.view.View;

import com.google.gson.annotations.SerializedName;
import com.nur_hidayat_agung.bkmmobile.util.UtilFunc;

import java.io.Serializable;
import java.util.Date;

public class PaySlip implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("driver_id")
    private String driver_id;
    @SerializedName("salary_trip")
    private String salary_trip;
    @SerializedName("bonus_trip")
    private String bonus_trip;
    @SerializedName("closing_date")
    private Date closing_date;
    @SerializedName("number_of_trip")
    private String number_of_trip;
    @SerializedName("point_trip")
    private String point_trip;
    @SerializedName("bonus_decrease")
    private String bonus_decrease;
    @SerializedName("insentif")
    private String insentif;
    @SerializedName("thr")
    private String thr;
    @SerializedName("bpjs_ket")
    private String bpjs_ket;
    @SerializedName("bpjs_kes")
    private String bpjs_kes;
    @SerializedName("pph21")
    private String pph21;
    @SerializedName("updated_by")
    private String updated_by;
    @SerializedName("updated_date")
    private Date updated_date;
    @SerializedName("created_by")
    private String created_by;
    @SerializedName("created_date")
    private Date created_date;
    @SerializedName("isactive")
    private Boolean isactive;
    @SerializedName("driver_name")
    private String driver_name;
    @SerializedName("gender")
    private String gender;
    @SerializedName("telp_number")
    private String telp_number;
    @SerializedName("norek")
    private String norek;
    @SerializedName("active_working_date")
    private Date active_working_date;
    @SerializedName("date_of_birth")
    private Date date_of_birth;
    @SerializedName("bpjs_number_employment")
    private String bpjs_number_employment;
    @SerializedName("bpjs_number_health")
    private String bpjs_number_health;
    @SerializedName("dept_until_month")
    private String dept_until_month;
    @SerializedName("remaining_dept")
    private String remaining_dept;
    @SerializedName("remaining_loan")
    private String remaining_loan;
    @SerializedName("dept_this_month")
    private String dept_this_month;
    @SerializedName("dept_prev_month")
    private String dept_prev_month;
    @SerializedName("loan_prev_month")
    private String loan_prev_month;
    @SerializedName("loan_until_month")
    private String loan_until_month;
    @SerializedName("loan_this_month")
    private String loan_this_month;
    @SerializedName("dept_payment")
    private String dept_payment;
    @SerializedName("loan_payment")
    private String loan_payment;
    @SerializedName("prev_closing_date")
    private Date prev_closing_date;
    @SerializedName("saving")
    private String saving;



    @SerializedName("adjustment")
    public String adjustment;

    public int isPlus;
    public int isMinus;

    public String getTotalIncome()
    {
        Double val = UtilFunc.forceNumber(salary_trip) + UtilFunc.forceNumber(bonus_trip)
                + UtilFunc.forceNumber(thr) + UtilFunc.forceNumber(insentif) +
                UtilFunc.forceNumber(bonus_decrease);

        if(adjustment != null)
        {
            if (UtilFunc.isNumeric(adjustment))
            {
                double adjusmetVal = Double.parseDouble(adjustment);
                if (adjusmetVal > 0)
                {
                    val = val + adjusmetVal;
                }
            }
        }

        return "+" + UtilFunc.currFormat(val);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getSalary_trip() {
        return UtilFunc.currFormat(salary_trip);
    }

    public void setSalary_trip(String salary_trip) {
        this.salary_trip = salary_trip;
    }

    public String getBonus_trip() {
        return UtilFunc.currFormat(bonus_trip);
    }

    public String get_Adjustment() {
        String data = adjustment;
        if(UtilFunc.isStringNotNull(data))
        {
            if (UtilFunc.isNumeric(data))
            {
                double adjustmentVal = Double.parseDouble(data);
                adjustmentVal = Math.abs(adjustmentVal);
                data = String.valueOf(adjustmentVal);
            }
        }
        return UtilFunc.currFormat(data);
    }

    public void setBonus_trip(String bonus_trip) {
        this.bonus_trip = bonus_trip;
    }

    public Date getClosing_date() {
        return closing_date;
    }

    public void setClosing_date(Date closing_date) {
        this.closing_date = closing_date;
    }

    public String getNumber_of_trip() {
        return number_of_trip;
    }

    public void setNumber_of_trip(String number_of_trip) {
        this.number_of_trip = number_of_trip;
    }

    public String getPoint_trip() {
        return point_trip;
    }

    public void setPoint_trip(String point_trip) {
        this.point_trip = point_trip;
    }

    public String getBonus_decrease() {
        return UtilFunc.currFormat(bonus_decrease);
    }

    public void setBonus_decrease(String bonus_decrease) {
        this.bonus_decrease = bonus_decrease;
    }

    public String getInsentif() {
        return UtilFunc.currFormat(insentif);
    }

    public void setInsentif(String insentif) {
        this.insentif = insentif;
    }

    public String getThr() {
        return UtilFunc.currFormat(thr);
    }

    public void setThr(String thr) {
        this.thr = thr;
    }

    public String getBpjs_ket() {
        return UtilFunc.currFormat(bpjs_ket);
    }

    public void setBpjs_ket(String bpjs_ket) {
        this.bpjs_ket = bpjs_ket;
    }

    public String getBpjs_kes() {
        return UtilFunc.currFormat(bpjs_kes);
    }

    public void setBpjs_kes(String bpjs_kes) {
        this.bpjs_kes = bpjs_kes;
    }

    public String getPph21() {
        return UtilFunc.currFormat(pph21);
    }

    public PaySlip() {
        isPlus = View.GONE;
        isMinus = View.GONE;
    }

    public String getTotalDeduction()
    {
        Double val = UtilFunc.forceNumber(bpjs_kes) + UtilFunc.forceNumber(bpjs_ket)
                + UtilFunc.forceNumber(pph21);

        if(adjustment != null)
        {
            if (UtilFunc.isNumeric(adjustment))
            {
                double adjusmetVal = Double.parseDouble(adjustment);
                adjusmetVal = Math.abs(adjusmetVal);
                if (adjusmetVal < 0)
                {
                    val = val + adjusmetVal;
                }
            }
        }

        return "-" + UtilFunc.currFormat(val);
    }

    public String getTotalPayment()
    {
        Double val = UtilFunc.forceNumber(dept_payment) + UtilFunc.forceNumber(loan_payment);
        return "-" + UtilFunc.currFormat(val);
    }

    public String getTHP()
    {
        double totalVal = 0;

        double val = UtilFunc.forceNumber(salary_trip) + UtilFunc.forceNumber(bonus_trip)
                + UtilFunc.forceNumber(thr) + UtilFunc.forceNumber(insentif) +
                UtilFunc.forceNumber(bonus_decrease);

        double val2 = UtilFunc.forceNumber(bpjs_kes) + UtilFunc.forceNumber(bpjs_ket)
                + UtilFunc.forceNumber(pph21);

        double val3 = UtilFunc.forceNumber(dept_payment) + UtilFunc.forceNumber(loan_payment);

        totalVal = val - val2 - val3;

        if(adjustment != null)
        {
            if (UtilFunc.isNumeric(adjustment))
            {
                double adjusmetVal = Double.parseDouble(adjustment);
                totalVal = totalVal + adjusmetVal;
            }
        }

        return "Rp " + UtilFunc.currFormat(totalVal);
    }

    public void setPph21(String pph21) {
        this.pph21 = pph21;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelp_number() {
        return telp_number;
    }

    public void setTelp_number(String telp_number) {
        this.telp_number = telp_number;
    }

    public String getNorek() {
        return norek;
    }

    public void setNorek(String norek) {
        this.norek = norek;
    }

    public Date getActive_working_date() {
        return active_working_date;
    }

    public void setActive_working_date(Date active_working_date) {
        this.active_working_date = active_working_date;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getBpjs_number_employment() {
        return bpjs_number_employment;
    }

    public void setBpjs_number_employment(String bpjs_number_employment) {
        this.bpjs_number_employment = bpjs_number_employment;
    }

    public String getBpjs_number_health() {
        return bpjs_number_health;
    }

    public void setBpjs_number_health(String bpjs_number_health) {
        this.bpjs_number_health = bpjs_number_health;
    }

    public String getDept_until_month() {
        return dept_until_month;
    }

    public void setDept_until_month(String dept_until_month) {
        this.dept_until_month = dept_until_month;
    }

    public String getRemaining_dept() {
//        return remaining_dept;
        return UtilFunc.currFormat(remaining_dept);
    }

    public void setRemaining_dept(String remaining_dept) {
        this.remaining_dept = remaining_dept;
    }

    public String getRemaining_loan() {
        return UtilFunc.currFormat(remaining_loan);
    }

    public void setRemaining_loan(String remaining_loan) {
        this.remaining_loan = remaining_loan;
    }

    public String getDept_this_month() {
        return dept_this_month;
    }

    public void setDept_this_month(String dept_this_month) {
        this.dept_this_month = dept_this_month;
    }

    public String getDept_prev_month() {
        return dept_prev_month;
    }

    public void setDept_prev_month(String dept_prev_month) {
        this.dept_prev_month = dept_prev_month;
    }

    public String getLoan_prev_month() {
        return loan_prev_month;
    }

    public void setLoan_prev_month(String loan_prev_month) {
        this.loan_prev_month = loan_prev_month;
    }

    public String getLoan_until_month() {
        return loan_until_month;
    }

    public void setLoan_until_month(String loan_until_month) {
        this.loan_until_month = loan_until_month;
    }

    public String getLoan_this_month() {
        return loan_this_month;
    }

    public void setLoan_this_month(String loan_this_month) {
        this.loan_this_month = loan_this_month;
    }

    public String getDept_payment() {
        return UtilFunc.currFormat(dept_payment);
    }

    public void setDept_payment(String dept_payment) {
        this.dept_payment = dept_payment;
    }

    public String getLoan_payment() {
        return UtilFunc.currFormat(loan_payment);
    }

    public void setLoan_payment(String loan_payment) {
        this.loan_payment = loan_payment;
    }

    public Date getPrev_closing_date() {
        return prev_closing_date;
    }

    public void setPrev_closing_date(Date prev_closing_date) {
        this.prev_closing_date = prev_closing_date;
    }

    public String getSaving() {
        return UtilFunc.currFormat(saving);
    }

    public void setSaving(String saving) {
        this.saving = saving;
    }
}
