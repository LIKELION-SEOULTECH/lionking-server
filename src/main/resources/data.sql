INSERT IGNORE INTO user (login_id, email, password) VALUES
('admin', 'representative1@likelion.org', 'admin'),
('manager1', 'manager1@likelion.org', 'password123'),
('manager2', 'manager2@likelion.org', 'password123'),
('user1', 'user1@likelion.org', 'password123'),
('user2', 'user2@likelion.org', 'password123'),
('user3', 'user3@likelion.org', 'password123');

INSERT IGNORE INTO member (username, position, role, generation, user_id, department, description_tag) VALUES
('김대표', 'FRONTEND', 'REPRESENTATIVE', 13, 1, '컴퓨터공학과', '열정적인 개발자'),
('박운영', 'BACKEND', 'MANAGER', 13, 2, '소프트웨어학과', '백엔드 전문가'),
('이매니저', 'FRONTEND', 'MANAGER', 13, 3, '정보통신학과', '프론트엔드 리더'),
('홍길동', 'BACKEND', 'USER', 13, 4, '컴퓨터공학과', '신입 개발자'),
('김철수', 'FRONTEND', 'USER', 13, 5, '소프트웨어학과', '프론트엔드 지망생'),
('이영희', 'PLAN', 'USER', 13, 6, '경영학과', '기획자');