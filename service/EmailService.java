package renewal.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(toEmail);
            helper.setSubject("[회원가입 인증] AwesomeTravel 이메일 인증");
            
            String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px;'>"
                    + "<h2 style='color: #333;'>이메일 인증</h2>"
                    + "<p style='color: #666; font-size: 16px;'>아래 버튼을 클릭하여 이메일 인증을 완료해주세요.</p>"
                    + "<a href='" + link + "' style='display: inline-block; padding: 12px 24px; background-color: #333; color: white; text-decoration: none; border-radius: 6px; margin: 20px 0; font-weight: bold;'>이메일 인증하기</a>"
                    + "<p style='color: #999; font-size: 12px; margin-top: 20px;'>버튼이 클릭되지 않을 경우 아래 링크를 복사하여 브라우저에 붙여넣으세요:</p>"
                    + "<p style='color: #666; font-size: 12px; word-break: break-all;'>" + link + "</p>"
                    + "</div>";
            
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송 실패", e);
        }
    }

    @Async
    public void sendMateMail(String toEmail, String token, String endPoint) {
        String link = frontendUrl + endPoint + token;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(toEmail);
            helper.setSubject("[여행메이트 등록] AwesomeTravel 이메일 인증");
            
            String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px;'>"
                    + "<h2 style='color: #333;'>여행메이트 등록 승인</h2>"
                    + "<p style='color: #666; font-size: 16px;'>아래 버튼을 클릭하여 여행메이트 등록을 승인해주세요.</p>"
                    + "<a href='" + link + "' style='display: inline-block; padding: 12px 24px; background-color: #333; color: white; text-decoration: none; border-radius: 6px; margin: 20px 0; font-weight: bold;'>여행메이트 승인하기</a>"
                    + "<p style='color: #999; font-size: 12px; margin-top: 20px;'>버튼이 클릭되지 않을 경우 아래 링크를 복사하여 브라우저에 붙여넣으세요:</p>"
                    + "<p style='color: #666; font-size: 12px; word-break: break-all;'>" + link + "</p>"
                    + "</div>";
            
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송 실패", e);
        }
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
