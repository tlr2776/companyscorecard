/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package companyscorecard;

import dashparser.Job;
import dashparser.DashParser;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is intended to create a simple txt file that can be copy/pasted into a
 * new column on the company scorecard excel file.
 *
 * @author tara
 */
public class CompileScorecard {

    private ArrayList<Job> jobList;
    private LocalDate today;
//    private LocalDate l;
    private LocalDate firstDate;
    private LocalDate lastDate;

    //these division ints are the counts for each, which will be reset after every run
    private int bu;
    private int content;
    private int fire;
    private int mold;
    private int recon;
    private int temp;
    private int water;
    private int totals;

    private WriteCard w;

    public CompileScorecard() {
        jobList = DashParser.getJobList();
        today = LocalDate.now();

        w = new WriteCard();

        //lastDate is found first--this will be the most recent Friday before the current day.
        //firstDate is lastDate minus 7
        for (int i = 0; i < 10; i++) {
            //leaving this at i<10 b/c if we reach 10 then something has obviously broken
            LocalDate d = today.minusDays(i);
            if (d.getDayOfWeek() == DayOfWeek.FRIDAY) {
                lastDate = d;
                //breaking to prevent an earlier Friday from overriding the most recent one
                break;
            }
        }
        if (lastDate == null) {
            System.out.println("Failed to find previous Friday.");
        } else {
            firstDate = lastDate.minusDays(6); //TODO check that this works out correctly
        }
        System.out.println("Searching in date range of " + firstDate.toString() + " to " + lastDate.toString());

        getLeads();
        getStarted();
        getProduction();
        getContracts();
        getBilled();
        w.endFile();

    }

    /**
     * Checks that the given date falls within the date range for the weekly
     * report.
     *
     * @param d
     */
    private boolean dateCheck(LocalDate d) {
        return d.compareTo(firstDate) >= 0 && d.compareTo(lastDate) <= 0;
    }

    private void getLeads() {
        for (Job job : jobList) {
            if (job.getReceivedDate() != null) {
                if (dateCheck(job.getReceivedDate())) {
                    assignDivs(job);
                }
            }
        }
        exportData();
    }

    private void getStarted() {
        for (Job job : jobList) {
            if (job.getStartedDate() != null) {
                if (dateCheck(job.getStartedDate())) {
                    assignDivs(job);
                }
            }
        }
        exportData();
    }

    private void getProduction() {
        for (Job job : jobList) {
            if (job.getJobStatus().equals("Work in Progress")) {
                assignDivs(job);
            }
        }
        exportData();
    }

    private void getContracts() {
        for (Job job : jobList) {
            if (job.getWorkAuthDate() != null) {
                if (dateCheck(job.getWorkAuthDate())) {
                    assignDivs(job);
                }
            }
        }
        exportData();
    }

    private void getBilled() {
        for (Job job : jobList) {
            if (job.getInvoicedDate() != null) {
                if (dateCheck(job.getInvoicedDate())) {
                    assignDivs(job);
                }
            }
        }
        w.dumpContents(totals);
        resetDivs();
    }

    private void assignDivs(Job job) {
        switch (job.getDivision()) {
            case "1800BU":
                bu++;
                break;
            case "Contents":
                content++;
                break;
            case "Fire Mitigation":
                fire++;
                break;
            case "Mold Remediation":
                mold++;
                break;
            case "Reconstruction":
                recon++;
                break;
            case "ClientRunner Migration":
                recon++;
                break;
            //the only CR jobs still open are big recon ones
            case "Temp Repairs":
                temp++;
                break;
            case "Water Mitigation":
                water++;
                break;
        }
        totals++;
    }

    private void resetDivs() {
        bu = 0;
        content = 0;
        fire = 0;
        mold = 0;
        recon = 0;
        temp = 0;
        water = 0;
        totals = 0;
    }

    private void exportData() {
        w.dumpContents(bu);
        w.dumpContents(content);
        w.dumpContents(fire);
        w.dumpContents(mold);
        w.dumpContents(recon);
        w.dumpContents(temp);
        w.dumpContents(water);
        w.dumpContents(totals);
        w.dumpContents();

        resetDivs();
    }

}
