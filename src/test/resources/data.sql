
INSERT INTO groups (group_name) VALUES ('ef-41');
INSERT INTO groups (group_name) VALUES ('ed-48');
INSERT INTO groups (group_name) VALUES ('TO-36');
INSERT INTO groups (group_name) VALUES ('er-48');


INSERT INTO students (group_id, first_name, last_name) VALUES (2, 'Betty', 'Walker');
INSERT INTO students (group_id, first_name, last_name) VALUES (1, 'Paul', 'Howard');
INSERT INTO students (group_id, first_name, last_name) VALUES (4, 'Thomas', 'Reed');
INSERT INTO students (group_id, first_name, last_name) VALUES (2, 'Mary', 'Lee');

INSERT INTO teachers (first_name, last_name) VALUES ('David', 'Kage');
INSERT INTO teachers (first_name, last_name) VALUES ('Robert', 'Mane');
INSERT INTO teachers (first_name, last_name) VALUES ('Roberto', 'Carlos');
INSERT INTO teachers (first_name, last_name) VALUES ('Bob', 'Strock');

INSERT INTO courses (teacher_id, course_name) VALUES (1, 'Astronomy');
INSERT INTO courses (teacher_id, course_name) VALUES (2, 'History');
INSERT INTO courses (teacher_id, course_name) VALUES (3, 'Programming');

INSERT INTO students_courses (student_id, course_id) VALUES (2, 1);
INSERT INTO students_courses (student_id, course_id) VALUES (3, 1);
INSERT INTO students_courses (student_id, course_id) VALUES (4, 2);
INSERT INTO students_courses (student_id, course_id) VALUES (1, 2);

INSERT INTO teachers_groups (teacher_id, group_id) VALUES (1, 4);
INSERT INTO teachers_groups (teacher_id, group_id) VALUES (2, 2);
INSERT INTO teachers_groups (teacher_id, group_id) VALUES (1, 2);
INSERT INTO teachers_groups (teacher_id, group_id) VALUES (2, 1);

INSERT INTO timetables (table_name, date) VALUES ('Today Table', '2020-12-14');
INSERT INTO timetables (table_name, date) VALUES ('Monday Table', '2020-12-15');
INSERT INTO timetables (table_name, date) VALUES ('Thuesday Table', '2020-12-16');

INSERT INTO schedules (group_id, course_id, teacher_id, timetable_id, date) VALUES (4, 1, 1, 1, '2020-11-19');
INSERT INTO schedules (group_id, course_id, teacher_id, timetable_id, date) VALUES (4, 1, 1, 1, '2020-12-19');
INSERT INTO schedules (group_id, course_id, teacher_id, timetable_id, date) VALUES (4, 1, 1, 1, '2020-07-30');
INSERT INTO schedules (group_id, course_id, teacher_id, timetable_id, date) VALUES (2, 2, 2, 1, '2020-07-30');