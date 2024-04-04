package com.scme.messenger.constants;

import org.springframework.stereotype.Component;

@Component
public abstract class MailContent {
    private MailContent() {
    }

    public final static String subject = "OTP Registeration Code for SCME Messenger";

    public static String MAIL_CONTENT(String username, String OTP) {
        return "<!DOCTYPE html>\n" + //
                "<html lang=\"en\">\n" + //
                "  <head>\n" + //
                "    <meta charset=\"UTF-8\" />\n" + //
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" + //
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\" />\n" + //
                "    <title>Static Template</title>\n" + //
                "\n" + //
                "    <link\n" + //
                "      href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap\"\n" + //
                "      rel=\"stylesheet\"\n" + //
                "    />\n" + //
                "  </head>\n" + //
                "  <body\n" + //
                "    style=\"\n" + //
                "      margin: 0;\n" + //
                "      font-family: 'Poppins', sans-serif;\n" + //
                "      background: #ffffff;\n" + //
                "      font-size: 14px;\n" + //
                "    \"\n" + //
                "  >\n" + //
                "    <div\n" + //
                "      style=\"\n" + //
                "        max-width: 680px;\n" + //
                "        margin: 0 auto;\n" + //
                "        padding: 45px 30px 60px;\n" + //
                "        background: #f4f7ff;\n" + //
                "        background-image: url(https://scme.edu.ps/wp-content/uploads/2023/05/night-gate-scaled.jpg);\n"
                + //
                "        background-repeat: no-repeat;\n" + //
                "        background-size: 800px 452px;\n" + //
                "        background-position: top center;\n" + //
                "        font-size: 14px;\n" + //
                "        color: #434343;\n" + //
                "      \"\n" + //
                "    >\n" + //
                "      <header>\n" + //
                "        <table style=\"width: 100%;\">\n" + //
                "          <tbody>\n" + //
                "            <tr style=\"height: 0;\">\n" + //
                "              <td>\n" + //
                "                <img\n" + //
                "                  alt=\"\"\n" + //
                "                  src=\"https://scme.edu.ps/wp-content/uploads/2023/04/SCME-Moodle-Logo-Trans.png\"\n"
                + //
                "                  height=\"30px\"\n" + //
                "                />\n" + //
                "              </td>\n" + //
                "              <td style=\"text-align: right;\">\n" + //
                "                <span\n" + //
                "                  style=\"font-size: 16px; line-height: 30px; color: #ffffff;\"\n" + //
                "                  >12 Nov, 2021</span\n" + //
                "                >\n" + //
                "              </td>\n" + //
                "            </tr>\n" + //
                "          </tbody>\n" + //
                "        </table>\n" + //
                "      </header>\n" + //
                "\n" + //
                "      <main>\n" + //
                "        <div\n" + //
                "          style=\"\n" + //
                "            margin: 0;\n" + //
                "            margin-top: 70px;\n" + //
                "            padding: 92px 30px 115px;\n" + //
                "            background: #ffffff;\n" + //
                "            border-radius: 30px;\n" + //
                "            text-align: center;\n" + //
                "          \"\n" + //
                "        >\n" + //
                "          <div style=\"width: 100%; max-width: 489px; margin: 0 auto;\">\n" + //
                "            <img\n" + //
                "                 style=\"margin: 0 auto; padding:0\"\n" + //
                "                  alt=\"\"\n" + //
                "                  src=\"https://scme.edu.ps/wp-content/uploads/2023/04/SCME-Moodle-Logo-Trans.png\"\n"
                + //
                "                  height=\"60px\"\n" + //
                "                />\n" + //
                "            <h1\n" + //
                "              style=\"\n" + //
                "                margin: 0;\n" + //
                "                font-size: 24px;\n" + //
                "                font-weight: 500;\n" + //
                "                color: #1f1f1f;\n" + //
                "              \"\n" + //
                "            >\n" + //
                "              Your OTP\n" + //
                "            </h1>\n" + //
                "            <p\n" + //
                "              style=\"\n" + //
                "                margin: 0;\n" + //
                "                margin-top: 17px;\n" + //
                "                font-size: 16px;\n" + //
                "                font-weight: 500;\n" + //
                "              \"\n" + //
                "            >\n" + //
                "              Hey " + username + ", \n" + //
                "            </p>\n" + //
                "            <p\n" + //
                "              style=\"\n" + //
                "                margin: 0;\n" + //
                "                margin-top: 17px;\n" + //
                "                font-weight: 500;\n" + //
                "                letter-spacing: 0.56px;\n" + //
                "              \"\n" + //
                "            >\n" + //
                "              Thank you for choosing SCME Messenger. Use the following OTP\n" + //
                "              to complete the procedure to Sign up. OTP is\n" + //
                "              valid for\n" + //
                "              <span style=\"font-weight: 600; color: #1f1f1f;\">5 minutes</span>.\n" + //
                "              Do not share this code with others, including SCME College.\n" + //
                "            </p>\n" + //
                "            <p\n" + //
                "              style=\"\n" + //
                "                margin: 0;\n" + //
                "                margin-top: 60px;\n" + //
                "                font-size: 40px;\n" + //
                "                font-weight: 600;\n" + //
                "                letter-spacing: 25px;\n" + //
                "                color: #ba3d4f;\n" + //
                "              \"\n" + //
                "            >\n" + //
                "              " + OTP + "\n" + //
                "            </p>\n" + //
                "          </div>\n" + //
                "        </div>\n" + //
                "      </main>\n" + //
                "\n" + //
                "      <footer\n" + //
                "        style=\"\n" + //
                "          width: 100%;\n" + //
                "          max-width: 490px;\n" + //
                "          margin: 20px auto 0;\n" + //
                "          text-align: center;\n" + //
                "          border-top: 1px solid #e6ebf1;\n" + //
                "        \"\n" + //
                "      >\n" + //
                "        <p\n" + //
                "          style=\"\n" + //
                "            margin: 0;\n" + //
                "            margin-top: 40px;\n" + //
                "            font-size: 16px;\n" + //
                "            font-weight: 600;\n" + //
                "            color: #434343;\n" + //
                "          \"\n" + //
                "        >\n" + //
                "          Smart College for Modern Education\n" + //
                "        </p>\n" + //
                "        <p style=\"margin: 0; margin-top: 8px; color: #434343;\">\n" + //
                "          Hebron, Palestine.\n" + //
                "        </p>\n" + //
                "        <div style=\"margin: 0; margin-top: 16px;\">\n" + //
                "          <a href=\"https://www.facebook.com/SCMEpal\" target=\"_blank\" style=\"display: inline-block;\">\n"
                + //
                "            <img\n" + //
                "              width=\"36px\"\n" + //
                "              alt=\"Facebook\"\n" + //
                "              src=\"https://archisketch-resources.s3.ap-northeast-2.amazonaws.com/vrstyler/1661502815169_682499/email-template-icon-facebook\"\n"
                + //
                "            />\n" + //
                "          </a>\n" + //
                "          <a\n" + //
                "            href=\"https://www.instagram.com/scmepal/\"\n" + //
                "            target=\"_blank\"\n" + //
                "            style=\"display: inline-block; margin-left: 8px;\"\n" + //
                "          >\n" + //
                "            <img\n" + //
                "              width=\"36px\"\n" + //
                "              alt=\"Instagram\"\n" + //
                "              src=\"https://archisketch-resources.s3.ap-northeast-2.amazonaws.com/vrstyler/1661504218208_684135/email-template-icon-instagram\"\n"
                + //
                "          /></a>\n" + //
                "          \n" + //
                "          <a\n" + //
                "            href=\"https://www.youtube.com/channel/UCJFTopZ08e-H1ppyGq4Trqw\"\n" + //
                "            target=\"_blank\"\n" + //
                "            style=\"display: inline-block; margin-left: 8px;\"\n" + //
                "          >\n" + //
                "            <img\n" + //
                "              width=\"36px\"\n" + //
                "              alt=\"Youtube\"\n" + //
                "              src=\"https://archisketch-resources.s3.ap-northeast-2.amazonaws.com/vrstyler/1661503195931_210869/email-template-icon-youtube\"\n"
                + //
                "          /></a>\n" + //
                "        </div>\n" + //
                "        <p style=\"margin: 0; margin-top: 16px; color: #434343;\">\n" + //
                "          Copyright © 2024 Company. All rights reserved.\n" + //
                "        </p>\n" + //
                "      </footer>\n" + //
                "    </div>\n" + //
                "  </body>\n" + //
                "</html>\n" + //
                "";
    }
}
