-- Inquiry 임시 데이터 (ORDER/REFUND/ACCOUNT/BENEFIT/SERVICE/POLICY/ETC)

DELETE FROM inquiry;

INSERT INTO inquiry
(id, user_id, title, content, category, status, product_id, purchase_id, stage,
 created_at, modified_at, created_by, modified_by)
VALUES
(1, 1, '결제 수단 변경', '해외 카드 대신 계좌이체로 결제 방법을 바꾸고 싶습니다.',
 'ORDER', 'PENDING', NULL, NULL, 'GENERAL', '2025-01-05 09:15:00', NULL, 'user1', NULL),
(2, 2, '영수증 재발급 요청', '법인 카드 영수증을 다시 받을 수 있을까요?',
 'ORDER', 'ANSWERED', NULL, NULL, 'GENERAL', '2025-01-06 11:30:00', '2025-01-06 15:10:00', 'user2', 'admin'),
(3, 3, '잔금 결제 확인', '잔금이 정상적으로 결제되었는지 확인 부탁드립니다.',
 'ORDER', 'PENDING', 4, 7001, 'AFTER_BOOKING', '2025-01-07 14:00:00', NULL, 'user3', NULL),
(4, 4, '결제 오류 문의', '결제 시도마다 오류가 발생합니다. 원인을 알고 싶습니다.',
 'ORDER', 'CLOSED', NULL, NULL, 'GENERAL', '2025-01-08 10:20:00', '2025-01-09 09:00:00', 'user4', 'admin'),

(5, 5, '환불 진행 상황', '취소 요청한 상품의 환불이 언제 완료되나요?',
 'REFUND', 'PENDING', 6, 7101, 'AFTER_BOOKING', '2025-01-10 13:40:00', NULL, 'user5', NULL),
(6, 6, '취소 수수료 문의', '출발 3일 전에 취소하면 수수료가 얼마인가요?',
 'REFUND', 'ANSWERED', NULL, NULL, 'BEFORE_PURCHASE', '2025-01-11 09:45:00', '2025-01-11 12:30:00', 'user6', 'admin'),
(7, 7, '부분 환불 가능 여부', '옵션만 부분적으로 환불 받을 수 있나요?',
 'REFUND', 'PENDING', 8, 7102, 'AFTER_BOOKING', '2025-01-12 13:20:00', NULL, 'user7', NULL),
(8, 8, '환불 완료 확인', '환불 완료 메일을 받았는데 입금이 되지 않았습니다.',
 'REFUND', 'CLOSED', 9, 7103, 'AFTER_BOOKING', '2025-01-13 11:15:00', '2025-01-13 16:05:00', 'user8', 'admin'),

(9, 9, '연락처 수정 요청', '회원정보의 연락처를 새 번호로 바꾸고 싶습니다.',
 'ACCOUNT', 'PENDING', NULL, NULL, 'GENERAL', '2025-01-14 15:35:00', NULL, 'user9', NULL),
(10, 10, '이메일 변경 문의', '가입 이메일을 회사 메일로 변경하고 싶습니다.',
 'ACCOUNT', 'ANSWERED', NULL, NULL, 'GENERAL', '2025-01-15 10:00:00', '2025-01-16 09:30:00', 'user10', 'admin'),
(11, 11, '비밀번호 초기화 문제', '재설정 링크가 만료되었다고 나옵니다.',
 'ACCOUNT', 'PENDING', NULL, NULL, 'GENERAL', '2025-01-16 08:20:00', NULL, 'user11', NULL),
(12, 12, '소셜 로그인 연동 해제', '카카오 연동을 끊고 일반 계정으로 전환하고 싶습니다.',
 'ACCOUNT', 'ANSWERED', NULL, NULL, 'GENERAL', '2025-01-17 09:10:00', '2025-01-17 11:40:00', 'user12', 'admin'),

(13, 13, '쿠폰 적용 실패', 'WELCOME 쿠폰을 적용했지만 가격이 변하지 않았습니다.',
 'BENEFIT', 'PENDING', NULL, NULL, 'GENERAL', '2025-01-18 14:55:00', NULL, 'user13', NULL),
(14, 14, '포인트 적립 누락', '이전 여행 포인트가 적립되지 않았습니다.',
 'BENEFIT', 'ANSWERED', 10, 7201, 'AFTER_TRAVEL', '2025-01-19 16:35:00', '2025-01-20 10:20:00', 'user14', 'admin'),
(15, 15, '이벤트 참여 확인', 'SNS 이벤트 참여가 정상적으로 접수되었나요?',
 'BENEFIT', 'PENDING', NULL, NULL, 'GENERAL', '2025-01-20 12:25:00', NULL, 'user15', NULL),
(16, 16, '적립금 사용 한도', '적립금을 전액 사용할 수 있는지 궁금합니다.',
 'BENEFIT', 'CLOSED', NULL, NULL, 'GENERAL', '2025-01-21 11:00:00', '2025-01-22 09:10:00', 'user16', 'admin'),

(17, 17, '앱 로그인 오류', '앱에서 로그인 시 흰 화면만 뜹니다.', 'SERVICE', 'PENDING',
 NULL, NULL, 'GENERAL', '2025-01-22 13:10:00', NULL, 'user17', NULL),
(18, 18, '결제 버튼 비활성화', '웹에서 결제 버튼이 클릭되지 않습니다.', 'SERVICE', 'ANSWERED',
 NULL, NULL, 'GENERAL', '2025-01-23 09:05:00', '2025-01-23 11:50:00', 'user18', 'admin'),
(19, 19, '푸시 알림 오류', '알림을 꺼도 계속 알림이 도착합니다.', 'SERVICE', 'PENDING',
 NULL, NULL, 'GENERAL', '2025-01-24 15:00:00', NULL, 'user19', NULL),
(20, 20, '화면 깨짐 현상', '태블릿에서 화면이 깨져 보입니다.', 'SERVICE', 'CLOSED',
 NULL, NULL, 'GENERAL', '2025-01-25 10:45:00', '2025-01-25 13:30:00', 'user20', 'admin'),

(21, 21, '개인정보 이용 문의', '위탁업체 목록을 확인하고 싶습니다.', 'POLICY', 'PENDING',
 NULL, NULL, 'GENERAL', '2025-01-26 09:15:00', NULL, 'user21', NULL),
(22, 22, '약관 변경 안내', '약관 변경 시점과 주요 내용을 알려주세요.', 'POLICY', 'ANSWERED',
 NULL, NULL, 'GENERAL', '2025-01-27 10:30:00', '2025-01-27 14:05:00', 'user22', 'admin'),
(23, 23, '동의 철회 방법', '마케팅 수신 동의를 철회하고 싶습니다.', 'POLICY', 'PENDING',
 NULL, NULL, 'GENERAL', '2025-01-28 12:50:00', NULL, 'user23', NULL),
(24, 24, '데이터 삭제 확인', '탈퇴 후 데이터가 완전히 삭제되었는지 확인 부탁드립니다.',
 'POLICY', 'CLOSED', NULL, NULL, 'GENERAL', '2025-01-29 16:45:00', '2025-01-30 09:00:00', 'user24', 'admin'),

(25, 25, '현지 가이드 연락 불가', '가이드님과 연락이 되지 않습니다.', 'SERVICE', 'PENDING',
 11, 7301, 'AFTER_TRAVEL', '2025-01-30 18:30:00', NULL, 'user25', NULL),
(26, 26, '호텔 체크인 문제', '예약된 이름이 달라 체크인이 거부되었습니다.', 'ORDER', 'ANSWERED',
 12, 7302, 'AFTER_BOOKING', '2025-01-31 09:20:00', '2025-01-31 11:45:00', 'user26', 'admin'),
(27, 27, '여행 후기 작성 위치', '여행 후기 작성 메뉴가 어디에 있나요?', 'BENEFIT', 'PENDING',
 13, 7303, 'AFTER_TRAVEL', '2025-02-01 11:05:00', NULL, 'user27', NULL),
(28, 28, '약관 위반 신고', '협력사가 약관을 위반한 것 같습니다.', 'POLICY', 'ANSWERED',
 NULL, NULL, 'GENERAL', '2025-02-02 15:45:00', '2025-02-03 10:30:00', 'user28', 'admin'),
(29, 29, '적립금 환불 요청', '적립금을 환불받을 수 있나요?', 'BENEFIT', 'PENDING',
 NULL, NULL, 'GENERAL', '2025-02-03 13:55:00', NULL, 'user29', NULL),
(30, 30, '기타 건의 사항', '앱 다크모드를 지원해주셨으면 합니다.', 'ETC', 'PENDING',
 NULL, NULL, 'GENERAL', '2025-02-04 12:25:00', NULL, 'user30', NULL);

