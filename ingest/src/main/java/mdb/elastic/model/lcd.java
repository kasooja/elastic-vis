package mdb.elastic.model;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class lcd implements ElasticDataModel {

	@JsonProperty("lcd_id")
	public String lcd_id;

	@JsonProperty("lcd_version")
	public String lcd_version;

	@JsonProperty("title")
	public String title;

	@JsonProperty("determination_number")
	public String determination_number;

	@JsonProperty("orig_det_eff_date")
	public Date orig_det_eff_date;

	@JsonProperty("orig_det_eff_dt_type")
	public String orig_det_eff_dt_type;

	@JsonProperty("ent_det_end_date")
	public String ent_det_end_date;

	@JsonProperty("rev_eff_date")
	public String rev_eff_date;

	@JsonProperty("rev_eff_dt_type")
	public String rev_eff_dt_type;

	@JsonProperty("rev_end_date")
	public String rev_end_date;

	@JsonProperty("indication")
	public String indication;

	@JsonProperty("diagnoses_support")
	public String diagnoses_support;

	@JsonProperty("icd9_dont_support_para")
	public String icd9_dont_support_para;

	@JsonProperty("icd9_dont_support_ast")
	public String icd9_dont_support_ast;

	@JsonProperty("diagnoses_dont_support")
	public String diagnoses_dont_support;

	@JsonProperty("coding_guidelines")
	public String coding_guidelines;

	@JsonProperty("doc_reqs")
	public String doc_reqs;

	@JsonProperty("appendices")
	public String appendices;

	@JsonProperty("util_guide")
	public String util_guide;

	@JsonProperty("source_info")
	public String source_info;

	@JsonProperty("adv_meeting")
	public String adv_meeting;

	@JsonProperty("comment_start_dt")
	public String comment_start_dt;

	@JsonProperty("comment_end_dt")
	public String comment_end_dt;

	@JsonProperty("notice_start_dt")
	public String notice_start_dt;

	@JsonProperty("rev_hist_num")
	public String rev_hist_num;

	@JsonProperty("history_exp")
	public String history_exp;

	@JsonProperty("last_reviewed_on")
	public String last_reviewed_on;

	@JsonProperty("status")
	public String status;

	@JsonProperty("last_updated")
	public String last_updated;

	@JsonProperty("cms_cov_policy")
	public String cms_cov_policy;

	@JsonProperty("display_id")
	public String display_id;

	@JsonProperty("draft_contact")
	public String draft_contact;

	@JsonProperty("sticky_note")
	public String sticky_note;

	@JsonProperty("revenue_para")
	public String revenue_para;

	@JsonProperty("thirty_percent")
	public String thirty_percent;

	@JsonProperty("keywords")
	public String keywords;

	@JsonProperty("associated_info")
	public String associated_info;

	@JsonProperty("notice_end_dt")
	public String notice_end_dt;

	@JsonProperty("date_retired")
	public String date_retired;

	@JsonProperty("draft_released_date")
	public String draft_released_date;

	@JsonProperty("source_lcd_id")
	public String source_lcd_id;

	@JsonProperty("add_icd10_info")
	public String add_icd10_info;

	@JsonProperty("icd10_doc")
	public String icd10_doc;

	@JsonProperty("synopsis_changes")
	public String synopsis_changes;

}