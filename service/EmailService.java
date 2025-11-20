package renewal.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import renewal.common.entity.User.MemberGrade;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Async
    public void sendVerificationMail(String toEmail, String token, String endPoint) {
        String link = frontendUrl + endPoint + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("[회원가입 인증] AwesomeTravel 이메일 인증");
        message.setText("아래 링크를 클릭해 이메일을 인증하세요:\n" + link);

        mailSender.send(message);
    }

    @Async
    public void sendMateMail(String toEmail, String token, String endPoint) {
        String link = frontendUrl + endPoint + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("[여행메이트 등록] AwesomeTravel 이메일 인증");
        message.setText("아래 링크를 클릭해 여행메이트 등록 승인해주세요:\n" + link);

        mailSender.send(message);
    }

    @Async
    public void sendWaitMail(String toEmail, String productTitle) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("[예약대기 알림] AwesomeTravel 예약 가능 알림");
        message.setText(productTitle + " 상품이 예약되었습니다! 상품 담당자가 연락 예정입니다.");

        mailSender.send(message);
    }

    @Async
    public void sendGradeMail(String toEmail, MemberGrade newGrade) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("[멤버십 승급 안내] AwesomeTravel 새 멤서십 등급 안내");
        message.setText("AwesomeTravel 멤버십 등급이 " + newGrade + "가 되었습니다!");

        mailSender.send(message);
    }
}
