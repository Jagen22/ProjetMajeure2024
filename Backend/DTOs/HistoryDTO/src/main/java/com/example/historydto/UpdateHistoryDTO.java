package com.example.historydto;

import java.util.Date;
import java.util.Map;

public class UpdateHistoryDTO {


		private String oldText;

		private String idProfile;

		private Map<Date,String> ListNewActivities;

		public UpdateHistoryDTO() {
			super();
		}
		public UpdateHistoryDTO(String text, String idProfile, Map<Date,String> ListNewActivities) {
			super();
			this.oldText = text;
		}

		public String getOldText() {
			return oldText;
		}
		public void setOldText(String oldText) {
			this.oldText = oldText;
		}
		public String getIdProfile() {
			return idProfile;
		}
		public void setIdProfile(String idProfile) {
			this.idProfile = idProfile;
		}
		public Map<Date,String> getListNewActivities() {
			return ListNewActivities;
		}
		public void setListNewActivities(Map<Date,String> listNewActivities) {
			ListNewActivities = listNewActivities;
		}
}
