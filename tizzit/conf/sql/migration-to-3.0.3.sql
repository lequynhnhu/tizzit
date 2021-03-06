# mysql
ALTER TABLE `content` ADD COLUMN `update_search_index` TINYINT DEFAULT 1;
ALTER TABLE `document` ADD COLUMN `update_search_index` TINYINT DEFAULT 1;
ALTER TABLE `edition` ADD COLUMN `edition_file_name` VARCHAR(255) NULL;
ALTER TABLE `edition` ADD COLUMN `needs_import` TINYINT DEFAULT 0 NULL;
ALTER TABLE `edition` ADD COLUMN `needs_deploy` TINYINT DEFAULT 0 NULL;
ALTER TABLE `edition` ADD COLUMN `use_new_ids` TINYINT DEFAULT 0 NULL;
ALTER TABLE `edition` ADD COLUMN `site_id` INTEGER NULL;
ALTER TABLE `edition` ADD COLUMN `view_component_id` INTEGER NULL;
ALTER TABLE `host` ADD COLUMN `liveserver` TINYINT DEFAULT 0;
ALTER TABLE `picture` ADD COLUMN `thumbnail_popup` TINYINT DEFAULT 0;
DROP TABLE COMP_PERSONTOUNITLINK;
ALTER TABLE `contentversion` DROP COLUMN `version_comment`;
# oracle
ALTER TABLE document ADD UPDATE_SEARCH_INDEX NUMBER(5) DEFAULT '1' NULL;
ALTER TABLE content ADD UPDATE_SEARCH_INDEX NUMBER(5) DEFAULT '1' NULL;
ALTER TABLE edition ADD edition_file_name VARCHAR2(255) NULL;
ALTER TABLE edition ADD needs_import NUMBER(5) DEFAULT 0 NULL;
ALTER TABLE edition ADD needs_deploy NUMBER(5) DEFAULT 0 NULL;
ALTER TABLE picture ADD thumbnail_popup NUMBER(5) DEFAULT 0 NULL;
ALTER TABLE edition ADD use_new_ids NUMBER(5) DEFAULT 0 NULL;
ALTER TABLE edition ADD site_id NUMBER(10) NULL;
ALTER TABLE edition ADD view_component_id NUMBER(10) NULL;
ALTER TABLE host ADD LIVESERVER NUMBER(5) DEFAULT '0' NULL;
DROP TABLE COMP_PERSONTOUNITLINK;
ALTER TABLE contentversion DROP COLUMN version_comment;
# sapdb
ALTER TABLE document ADD (UPDATE_SEARCH_INDEX SMALLINT DEFAULT '1' NULL);
ALTER TABLE content ADD (UPDATE_SEARCH_INDEX SMALLINT DEFAULT '1' NULL);
ALTER TABLE edition ADD (edition_file_name VARCHAR(255) NULL);
ALTER TABLE edition ADD (needs_import SMALLINT DEFAULT 0 NULL);
ALTER TABLE edition ADD (needs_deploy SMALLINT DEFAULT 0 NULL);
ALTER TABLE edition ADD (use_new_ids SMALLINT DEFAULT 0 NULL);
ALTER TABLE picture ADD (thumbnail_popup SMALLINT DEFAULT 0 NULL);
ALTER TABLE edition ADD (site_id INTEGER NULL);
ALTER TABLE edition ADD (view_component_id INTEGER NULL);
ALTER TABLE host ADD (LIVESERVER SMALLINT DEFAULT '0' NULL);
DROP TABLE COMP_PERSONTOUNITLINK;
ALTER TABLE contentversion DROP COLUMN version_comment;
# mssql
ALTER TABLE document ADD COLUMN UPDATE_SEARCH_INDEX SMALLINT DEFAULT '1' NULL;
ALTER TABLE content ADD COLUMN UPDATE_SEARCH_INDEX SMALLINT DEFAULT '1' NULL;
ALTER TABLE edition ADD COLUMN edition_file_name VARCHAR(255) NULL;
ALTER TABLE edition ADD COLUMN needs_import SMALLINT DEFAULT 0 NULL;
ALTER TABLE edition ADD COLUMN needs_deploy SMALLINT DEFAULT 0 NULL;
ALTER TABLE edition ADD COLUMN use_new_ids SMALLINT DEFAULT 0 NULL;
ALTER TABLE picture ADD COLUMN thumbnail_popup SMALLINT DEFAULT 0 NULL;
ALTER TABLE edition ADD COLUMN site_id INTEGER NULL;
ALTER TABLE edition ADD COLUMN view_component_id INTEGER NULL;
ALTER TABLE host ADD COLUMN LIVESERVER SMALLINT DEFAULT '0' NULL;
DROP TABLE COMP_PERSONTOUNITLINK;
ALTER TABLE contentversion DROP COLUMN version_comment;
Add to tizzit.properties
# EDITION MANAGEMENT ##########################################################
tizzitPropertiesBeanSpring.editionCronExpression=0 * * * * ?
