DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups
(
	group_id INT AUTO_INCREMENT  PRIMARY KEY,
	group_name VARCHAR(10) NOT NULL
);
DROP TABLE IF EXISTS students CASCADE;
CREATE TABLE students
(
	student_id INT AUTO_INCREMENT  PRIMARY KEY,
	group_id INTEGER REFERENCES groups (group_id) ON UPDATE CASCADE ON DELETE CASCADE,
	first_name VARCHAR(25) NOT NULL,
	last_name VARCHAR(25) NOT NULL
);
DROP TABLE IF EXISTS teachers CASCADE;
CREATE TABLE teachers
(
	teacher_id INT AUTO_INCREMENT  PRIMARY KEY,
	first_name VARCHAR(25) NOT NULL,
	last_name VARCHAR(25) NOT NULL
);
DROP TABLE IF EXISTS courses CASCADE;
CREATE TABLE courses
(
    course_id INT AUTO_INCREMENT  PRIMARY KEY,
	teacher_id INTEGER REFERENCES teachers (teacher_id) ON UPDATE CASCADE ON DELETE CASCADE,
	course_name VARCHAR(100) NOT NULL
);
DROP TABLE IF EXISTS students_courses CASCADE;
CREATE TABLE students_courses
(
	id INT AUTO_INCREMENT  PRIMARY KEY,
	student_id INTEGER REFERENCES students (student_id) ON UPDATE CASCADE ON DELETE CASCADE,
	course_id  INTEGER REFERENCES courses (course_id) ON UPDATE CASCADE ON DELETE CASCADE
);
DROP TABLE IF EXISTS teachers_groups CASCADE;
CREATE TABLE teachers_groups
(
	id INT AUTO_INCREMENT  PRIMARY KEY,
	teacher_id INTEGER REFERENCES teachers (teacher_id) ON UPDATE CASCADE ON DELETE CASCADE,
	group_id  INTEGER REFERENCES groups (group_id) ON UPDATE CASCADE ON DELETE CASCADE
);
DROP TABLE IF EXISTS timetables CASCADE;
CREATE TABLE timetables
(
	id INT AUTO_INCREMENT  PRIMARY KEY,
	table_name VARCHAR(50) NOT NULL,
	date date
);
DROP TABLE IF EXISTS schedules CASCADE;
CREATE TABLE schedules
(
	schedule_id INT AUTO_INCREMENT  PRIMARY KEY,
	group_id INTEGER REFERENCES groups (group_id) ON UPDATE CASCADE ON DELETE CASCADE,
	course_id INTEGER REFERENCES courses (course_id) ON UPDATE CASCADE ON DELETE CASCADE,
	teacher_id INTEGER REFERENCES teachers (teacher_id) ON UPDATE CASCADE ON DELETE CASCADE,
	timetable_id INTEGER REFERENCES timetables (id) ON UPDATE CASCADE ON DELETE CASCADE,
	date date
);


CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;