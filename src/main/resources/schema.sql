
drop table if exists user_table cascade;
drop table if exists department cascade;
drop table if exists module_table cascade;
drop table if exists course cascade;
drop table if exists course_user cascade;
drop table if exists chat cascade;
drop table if exists chat_message cascade;
drop table if exists group_message cascade;



CREATE TABLE IF NOT EXISTS user_table(
	user_id varchar(100)  NOT NULL,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL UNIQUE,
    role varchar(100) NOT NULL,
    password varchar(200) DEFAULT NULL,
    created_at date NOT NULL,
	created_by varchar(20) NOT NULL,
	updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL,
	CONSTRAINT PK_User PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS department(
	dep_id varchar(100) NOT NULL,
    name varchar(100) NOT NULL,
    manager_id int NOT NULL,
    created_at date NOT NULL,
	created_by varchar(20) NOT NULL,
	updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL,
	CONSTRAINT PK_Department PRIMARY KEY (dep_id)
);


CREATE TABLE IF NOT EXISTS module_table(
	module_id varchar(100)  NOT NULL,
    name varchar(100) NOT NULL,
    dep_id varchar(100) NOT NULL,
    created_at date NOT NULL,
	created_by varchar(20) NOT NULL,
	updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL,
	CONSTRAINT PK_Module PRIMARY KEY (module_id)
);



CREATE TABLE IF NOT EXISTS course(
	course_id SERIAL,
    module_id varchar(100) NOT NULL,
    name varchar(100) NOT NULL,
    teacher_id varchar(100) NOT NULL,
    members int DEFAULT 1,
    image_path varchar(200) DEFAULT 'NON',
    created_at date NOT NULL,
	created_by varchar(20) NOT NULL,
	updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL,
    CONSTRAINT PK_Course PRIMARY KEY (course_id , module_id)
);


CREATE TABLE IF NOT EXISTS course_user(
	user_id varchar(100)  NOT NULL,
	course_id varchar(100)  NOT NULL,
    module_id varchar(100) NOT NULL,
    created_at date NOT NULL,
	created_by varchar(20) NOT NULL,
	updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL,
    CONSTRAINT PK_Course_User PRIMARY KEY (course_id , module_id , user_id)
);



CREATE TABLE IF NOT EXISTS chat(
	chat_id uuid NOT NULL,
	sender_id varchar(100)  NOT NULL,
    recepient_id varchar(100) NOT NULL,
    created_at date NOT NULL,
	created_by varchar(20) NOT NULL,
	updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL,
    CONSTRAINT PK_Chat PRIMARY KEY (chat_id , sender_id , recepient_id)
);



CREATE TABLE IF NOT EXISTS chat_message(
	message_id uuid NOT NULL,
	chat_id uuid NOT NULL,
	sender_id varchar(100)  NOT NULL,
    recepient_id varchar(100) NOT NULL,
    created_at date NOT NULL,
	created_by varchar(20) NOT NULL,
	updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL,
    CONSTRAINT PK_Chat_Message PRIMARY KEY (chat_id , sender_id , recepient_id , message_id)
);



CREATE TABLE IF NOT EXISTS group_message(
	message_id uuid,
    course_id varchar(100) NOT NULL,
    module_id varchar(100) NOT NULL,
    sender_id varchar(100)  NOT NULL,
    created_at date NOT NULL,
	created_by varchar(20) NOT NULL,
	updated_at date DEFAULT NULL,
    updated_by varchar(20) DEFAULT NULL,
    CONSTRAINT PK_Group_Message PRIMARY KEY (message_id , course_id , module_id)
);


-- FOREIGN KYES
ALTER TABLE department ADD CONSTRAINT FK_Department_User FOREIGN KEY (manager_id) REFERENCES user_table(user_id);
ALTER TABLE module_table ADD CONSTRAINT FK_Module_Dept FOREIGN KEY (dep_id) REFERENCES department(dep_id);
ALTER TABLE course ADD CONSTRAINT FK_Course_User FOREIGN KEY (teacher_id) REFERENCES user_table(user_id) ;
ALTER TABLE course ADD CONSTRAINT FK_Course_Module FOREIGN KEY (module_id) REFERENCES module_table(module_id) ON DELETE CASCADE;
ALTER TABLE course_user ADD CONSTRAINT FK_Course_User_Course FOREIGN KEY (course_id , course_id) REFERENCES course(course_id , module_id) ON DELETE CASCADE;
ALTER TABLE course_user ADD CONSTRAINT FK_Course_User_User FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE;
ALTER TABLE chat ADD CONSTRAINT FK_Chat_Sender FOREIGN KEY (sender_id) REFERENCES user_table(user_id) ON DELETE CASCADE;
ALTER TABLE chat ADD CONSTRAINT FK_Chat_Recepient FOREIGN KEY (recepient_id) REFERENCES user_table(user_id) ON DELETE CASCADE;
ALTER TABLE chat_message ADD CONSTRAINT FK_Chat_Message_Sender FOREIGN KEY (sender_id) REFERENCES user_table(user_id) ON DELETE CASCADE;
ALTER TABLE chat_message ADD CONSTRAINT FK_Chat_Message_Recepient FOREIGN KEY (recepient_id) REFERENCES user_table(user_id) ON DELETE CASCADE;
ALTER TABLE chat_message ADD CONSTRAINT FK_Chat_Message_Chat FOREIGN KEY (chat_id , sender_id , sender_id) REFERENCES chat(chat_id , sender_id , recepient_id) ON DELETE CASCADE;
ALTER TABLE group_message ADD CONSTRAINT FK_Group_Message_Course FOREIGN KEY (course_id , module_id) REFERENCES course(course_id , module_id) ON DELETE CASCADE;
ALTER TABLE group_message ADD CONSTRAINT FK_Group_Message_User FOREIGN KEY (sender_id) references user_table(user_id) ON DELETE CASCADE;




