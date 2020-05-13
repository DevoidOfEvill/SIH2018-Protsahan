package com.mota.tribal.protsahan.Schemes.Model;

import android.os.Handler;

import com.mota.tribal.protsahan.Schemes.Model.Data.InnerData;
import com.mota.tribal.protsahan.Schemes.Model.Data.SchemeInfo;
import com.mota.tribal.protsahan.Schemes.SchemeCallback;

import java.util.ArrayList;
import java.util.List;

public class MockScheme implements SchemeProvider {
    private List<List<InnerData>> datalist;
    private List<InnerData> innerDataList;
    private List<String> schemename;
    private InnerData data;
    private SchemeInfo mockdata;

    @Override
    public void getSchemes(final SchemeCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(getMockData());
            }
        }, 500);
    }

    public SchemeInfo getMockData() {

        innerDataList = new ArrayList<>();
        datalist = new ArrayList<>();
        schemename = new ArrayList<>();
        schemename.add("TRI");
        schemename.add("Livelihood Support");
        schemename.add("Equity support to NSTFDC/STFDC");
        schemename.add("NGO");
        data = new InnerData("Scheme 1", "Support to Tribal Research Institutes (TRI)", "The basic objective of the scheme is to strengthen the Tribal Research Institutes (TRIs) in their infrastructural needs, Research & documentation activities and Training & Capacity Building programmers etc.More info at https://tribal.nic.in/DivisionsFiles/TRI/ModifiedGuidelines.pdf", "https://www.shethepeople.tv/wp-content/uploads/2016/10/tribeslide11.jpg", 2017);
        innerDataList.add(data);
        data = new InnerData("Scheme 2", "Financial Assistance for support to Centres of Excellence", " The objective is to enhance and strengthen the institutional resource capabilities of various NGOs, Research Institutes and University Departments to conduct qualitative, action oriented and policy research on tribal communities. To enhance and upgrade the existing skills, knowledge and technical know-how of the NGOs, Research Institutes and University Departments so that they may be able to uphold the cultural diversity of the Scheduled Tribes of the country and their empowerment. To enhance the efficiency of existing institutions for devising appropriate strategies for tribal development in partnership with the Ministry of Tribal Affairs. More info at https://tribal.nic.in/writereaddata/Schemes/2-2RM-SchemeofCoE.pdf", "https://blog.ipleaders.in/wp-content/uploads/2016/06/nagaland-15.jpg", 2017);
        innerDataList.add(data);
        data = new InnerData("Scheme 3", "Support to Tribal Research Institutes (TRI)", "The basic objective of the scheme is to strengthen the Tribal Research Institutes (TRIs) in their infrastructural needs, Research & documentation activities and Training & Capacity Building programmers etc. More info at https://tribal.nic.in/DivisionsFiles/TRI/ModifiedGuidelines.pdf", "https://www.google.co.in/search?q=artisans&source=lnms&tbm=isch&sa=X&ved=0ahUKEwii5umrzv3ZAhUBqI8KHWKYD_wQ_AUICigB&biw=1366&bih=662#imgrc=sz7jC94kJF2rTM:", 2017);
        innerDataList.add(data);
        data = new InnerData("Scheme 4", " Financial Assistance for support to Centres of Excellence ", " The objective is to enhance and strengthen the institutional resource capabilities of various NGOs, Research Institutes and University Departments to conduct qualitative, action oriented and policy research on tribal communities. To enhance and upgrade the existing skills, knowledge and technical know-how of the NGOs, Research Institutes and University Departments so that they may be able to uphold the cultural diversity of the Scheduled Tribes of the country and their empowerment. To enhance the efficiency of existing institutions for devising appropriate strategies for tribal development in partnership with the Ministry of Tribal Affairs. More info at https://tribal.nic.in/writereaddata/Schemes/2-2RM-SchemeofCoE.pdf", "https://www.shethepeople.tv/wp-content/uploads/2016/10/tribeslide11.jpg", 2015);
        innerDataList.add(data);
        data = new InnerData("Scheme 5", "Institutional support for marketing and development of tribal products ", " fixation- of equitable prices for existing products both manmade and natural, training for improved production and higher grade products and Setting up of efficient warehousing facilities, go downs, storage wherever necessary. More info at https://tribal.nic.in/writereaddata/Schemes/3-1MJGuidelines.pdf”,https://www.google.co.in/search?biw=1366&bih=662&tbm=isch&sa=1&ei=P2qyWpXZA4rqvgScv7SwCg&q=artisans+doing+Embroidery&oq=artisans+doing+Embroidery&gs_l=psy-ab.3...70750.78879.0.80094.17.17.0.0.0.0.264.2747.0j8j5.13.0....0...1c.1.64.psy-ab..4.2.402...0j0i67k1.0.6JfRbb3aE8E#imgrc=JhQ8xBuVCPMItM:", "https://www.slideshare.net/nalish2889/overview-of-ntfp-53609626", 2014);
        innerDataList.add(data);
        data = new InnerData("Scheme 6", " Mechanism for marketing of minor forest (MFP) produce through minimum support price (MSP) through and development of value chain for MFP.", " The basic objective of this scheme is to ensure fair returns to MFP gatherers through minimum support price for indentified MFP collected by them along with necessary infrastructure at local level. More info at https://tribal.nic.in/writereaddata/Schemes/3-B-MJ-MSPtoMFPguidlines2.pdf. ", "https://www.slideshare.net/nalish2889/overview-of-ntfp-53609626", 2013);
        innerDataList.add(data);
        data = new InnerData("Scheme 7", " Revision of guidelines of scheme “Mechanism for marketing of minor forest (MFP) produce through minimum support price (MSP) through and development of value chain for MFP ", " As regards of infrastructure building, fund for training should be given on the basis o performance. Funds for training and capacity building may be also given to state agency.More info at https://tribal.nic.in/writereaddata/Schemes/3-A-MJ-MSPtoMFP.pdf. ", "https://www.slideshare.net/nalish2889/overview-of-ntfp-53609626", 2015);
        innerDataList.add(data);
        data = new InnerData("Scheme 8", " Equity support to NSTFDC/STFDC ", " Scheme for Release of Equity Support to the National/ State Scheduled Tribes Finance and Development Corporations (STFDCs) is a continuing centrally sponsored scheme under which Central Government provides equity support to National Scheduled Tribes Finance and Development Corporation (NSTFDC) under the Ministry of Tribal Affairs and State Scheduled Tribes Finance and Development Corporations (STFDCs) of various State Governments. STFDCs catering to STs in various states are provided assistance towards their Share Capital under the centrally sponsored scheme. The ratio of Share Capital contribution between the centralandstategovernmentisof49:51.More info at https://tribal.nic.in/writereaddata/Schemes/EquitysupporttoNSTFDC.pdf", "https://blog.ipleaders.in/wp-content/uploads/2016/06/nagaland-15.jpg", 2011);
        innerDataList.add(data);
        data = new InnerData("Scheme 9", " Vocational training in tribal areas ", " Scheduled Tribes are the most marginalized section of the society, therefore to assist their socio-economic development, there is an imperative need to provide more employment avenues and income generation opportunities. It is aimed at upgrading the skills of the tribal youths in various traditional/modern vocations depending upon their educational qualification, present economic trends and the market potential, which would enable them to gain suitable employment or enable them to become self employed. More info at https://tribal.nic.in/writereaddata/Schemes/VTCGuidelinesAndApplicationFormat.pdf", "https://www.shethepeople.tv/wp-content/uploads/2016/10/tribeslide11.jpg", 2017);
        innerDataList.add(data);
        data = new InnerData("Scheme 10", " Development of particularly vulnerable tribe groups (PVTGs)", " The scheme aims at planning the socio-economic development of particularly vulnerable tribe groups in a comprehensive manner while retaining the culture and heritage of the community by adopting habitat development approach and intervening in all spheres of their social and economic life, so that a visible impact is made in improvement of the quality of life of PVTGs.More info at https://tribal.nic.in/writereaddata/Schemes/4-5NGORevisedScheme.pdf", "https://www.shethepeople.tv/wp-content/uploads/2016/10/tribeslide11.jpg", 2016);
        innerDataList.add(data);
        data = new InnerData("Scheme 11", " Strengthening Education among Scheduled Tribe (ST) Girls in Low Literacy Districts ", " The scheme aims to bridge the gap in literacy levels between the general female population and tribal women, through facilitating 100% enrolment of tribal girls in the identified Districts or Blocks, more particularly in naxal affected areas and in areas inhabited by Primitive Tribal Groups (PTGs), and reducing drop-outs at the elementary level by creating the required ambience for education. More info at https://tribal.nic.in/writereaddata/Schemes/46NGOSchemeStrengthningEducation.pdf", "https://blog.ipleaders.in/wp-content/uploads/2016/06/nagaland-15.jpg", 2013);
        innerDataList.add(data);
        data = new InnerData("Scheme 12", " Special central assistance(SCA) to tribal sub plan(TSP)", " This scheme is hereby laid down for the allocation of funds and to use SCA to TSP. TSP has objective is to fill the gap between scheduled tribes population and other by accelerating development of ST’s by ensuring: Human resource development by providing them education and health services. Enhanced quality of life by providing basic amenities in tribal areas including housing. More info at https://tribal.nic.in/DivisionsFiles/sg/8scan0004.pdf", "https://www.google.co.in/search?biw=1366&bih=662&tbm=isch&sa=1&ei=8WqyWv6CNoqGvQSE8bXYDw&q=artisans+doing+bamboo+handcraft&oq=artisans+doing+bamboo+handcraft&gs_l=psy-ab.3...141945.149634.0.150915.33.26.0.0.0.0.564.5092.0j5j8j2j1j2.18.0....0...1c.1.64.psy-ab..18.0.0....0._fMlmD0S44c#imgrc=ZxlaQMBsLsDS9M:", 2014);
        innerDataList.add(data);
        datalist.add(innerDataList);
        datalist.add(innerDataList);
        datalist.add(innerDataList);
        datalist.add(innerDataList);
        mockdata = new SchemeInfo(datalist, schemename, "Success", true);

        return mockdata;
    }
}
