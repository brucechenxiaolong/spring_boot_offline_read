--修改：files_text 字段类型为 BLOB
CREATE TABLE use_approve (
	id VARCHAR (32) NOT NULL,
	approve_id VARCHAR (32),
	form_id VARCHAR (32),
	table_id VARCHAR (32),
	entity_id VARCHAR (32),
	entity_text BLOB,
	files_text BLOB,
	state INT DEFAULT 0,
	entity_view_permission INT DEFAULT 0,
	file_view_permission INT DEFAULT 0,
	outstock_permission INT DEFAULT 0,
	expired INTEGER DEFAULT 0,
	file_down_permission INT DEFAULT 0,
	file_print_permission INT DEFAULT 0,
	modified INT DEFAULT 0,
	CONSTRAINT USE_APPROVE_PK PRIMARY KEY (id)
);
CREATE UNIQUE INDEX sqlite_autoindex_use_approve_1 ON use_approve (id);