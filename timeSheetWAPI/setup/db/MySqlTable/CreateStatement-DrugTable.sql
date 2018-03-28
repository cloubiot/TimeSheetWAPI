CREATE TABLE `drug` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DRUG_NAME` varchar(255) NOT NULL,
  `dosage_Administration` longtext,
  `dosage_Form_Strength` longtext,
  `contraindictions` longtext,
  `warnings_Precaustions` longtext,
  `adverse_Reactions` longtext,
  `drug_Interactions` longtext,
  `use_In_Specific_Populations` longtext,
  `AVAILABLE_AT_AVELLA` int(11) NOT NULL DEFAULT '1',
  `DRUG_CATEGORY_ID` int(11) NOT NULL,
  `SITE_URL` varchar(255) DEFAULT NULL,
  `indications_usage` longtext,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
