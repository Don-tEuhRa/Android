# Android
제 10회 SW개발보안 경진대회 소개딩 Android입니다.

# 코딩 컨벤션

- 네이밍 컨벤션
   
   변수, 함수 : camelCase 사용.    ex) var myAge = 24

   파일 명 : pascalCase 사용 ex) MainScreen
   
   상수 : uppercased underscore-separated 사용.    ex) const val MY_AGE = 24
   
   이미지, font 등 파일 : snake_case 사용.    ex) suite_bold.ttf
- 그 외 : 코틀린 공식 컨벤션

참고 : https://kotlinlang.org/docs/coding-conventions.html#trailing-commas


# commit message 양식

기본적으로 Issues에 있는 Label의 양식을 따른다.
| Label             | 내용                                                                         |
| ----------------- | --------------------------------------------------------------------------- |
| FEAT              |  새로운 기능 추가(개발)                                                          |
| DOCS              |  문서 수정                                                                    |
| FIX               |  버그 수정                                                                     |
| REFACTOR          |  코드 리펙토링                                                                  |
| STYLE             |  코딩스타일 변경(코드 자체에는 변경 없음)                                             |

위와 같은 Label을 맨 앞에 적고 작업내용을 제목으로 작성  
세부사항은 아래 description에 적기  
기능을 여러개 구현했을 경우 상세 내용에서 문단 구분하여 작성하거나, 나눠서 commit  

EX)
| **[FEAT] Login Page, Basic Page Format 구현**         |
| ---------------------------------------------------- |
| Splash Screen 후 Login Page로 넘어가도록 구현<br/>버튼을 누르면 메인페이지로 넘어가도록 설정<br/>OAuth와 같은 기능적인 부분은 추후 구현 예정<br/> <br/> Basic Page Format 완성 <br/>하단바에 4개의 아이콘 사용. 메인, 마이페이지, 커뮤니티, 설정 구현 <br/>임시 포인트 컬러 파란색으로 구현 <br/>각 페이지에 있을 경우 해당 아이콘 포인트 컬러로 나타냄<br/> OnceScreen을 통해 재사용성 증가  |
