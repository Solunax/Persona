# Persona
University Team Project

- 대학 분석설계 프로젝트 소스 및 전체 파일
- Firebase RealTime Database, Firebase Storage, Android Studio 사용  
- 팀장 박경호
- 팀원 : 임민교, 이시진  

- 작업 내역  
박경호 : 로그인, 성우 회원가입, 내 정보 보기, 회원정보 수정, 비밀번호 변경
         연기샘플 등록ㆍ수정ㆍ삭제ㆍ확인, 목소리 검색, 성우 매칭
         공고 등록ㆍ수정ㆍ매칭
         공고 지원, 공고 연기샘플 수정
         리뷰 등록ㆍ수정ㆍ삭제ㆍ확인  
임민교 : 구매자 회원가입, 자기소개/약력 입력, 회원 탈퇴  
이시진 : UI/UX  

- 프로젝트는 크게 기획 → 분석 → 구현의 세 단계로 진행했습니다.
- 최초 프로젝트 시작시 인원은 5명. 중간에 팀원들의 개인 사정으로 인해 자퇴를 결정하게 되면서 기획 이후의 단계부터는 3인으로 전부 이루어졌습니다.  
  
1. 기획
- 무슨 프로젝트를 할지 결정하고, 프로젝트에 대한 조사를 진행한 단계입니다.
- 다양한 프로젝트 주제 후보들이 있었으나, 성우와 클라이언트의 매칭을 성사시키는 앱을 제작하기로 기획하였습니다.  
- 시장성 조사와 SWOT 분석을 진행하여 기존의 웹사이트와 앱들과는 다른 우리 앱만의 차별화 요소를 찾았습니다.
- 인공지능(AI)을 통한 목소리 분석기능을 추가하여 성우 목소리 특색을 뽑아주는 기능을 추가할 계획을 세웠습니다.  
  
2. 분석
- 앱에 필요한 화면 구성부터 DB등을 구성한 단계입니다.
- 분석은 스토리보드 위주로 진행하였습니다.
- 앱에 전체적인 플로우차트를 작성하여 앱의 흐름을 전개하고, 그 흐름에 맞게 스토리보드를 작성하였습니다.
- 작성한 스토리보드를 바탕으로 DB에 필요한 객체나 속성, 관계를 추출하고 이를 논리ERD로 만들었습니다.
- 앱의 백엔드로 FireBase를 사용하기에 논리 ERD를 FireBase RealTime DataBase의 NoSQL 형식에 맞게 변형하였습니다.  

3. 구현
- 앱을 실직적으로 제작한 단계입니다.
- Firebase는 Google이 제공하는 Firebase Documentation를 통해 사용법을 익히고, 이를 앱에 적용시켰습니다.
- 부가적으로 학교 강의때 다 배우지 못한 기능들은 Anrdroid Developer 등의 웹 사이트에서 찾아서 구현했습니다.
- 기간 부족으로 인해 아이디ㆍ비밀번호 찾기, 연기샘플 검색과 인공지능을 통한 목소리 특징 분석기능은 구현하지 못했습니다.

4. 소감
- 처음으로 프로젝트의 팀장을 맡아서 부담감도 크고, 해야할 업무를 잘못 분담하면 프로젝트 진행이 느려질 수 있다는 압박감도 있었습니다.
그래도 팀원을 잘 이끌어 해당 과목에서 좋은 성적을 거두었습니다.
- 구현만을 목적으로 최대한 빠르게 코드를 작성하고, 능력도 부족해서 코드도 지저분하고, 객체화도 잘 이루어져있지 못합니다. 생명주기에 대한 내용을 프로젝트 구현의 막바지에 알게되어서 이 부분은 거의 신경을 쓰지 못했습니다.
- 앞으로 기본적 내용과 더불어 학습에 정진하여 최대한 간결하고 오류 없는 앱을 만들 수 있게 노력하겠습니다.

4-1. 소통의 중요성
- 처음에는 업무를 어떻게 분담해야할지, 기간은 얼마만큼 줘야할지 등의 많은 어려움에 부딫혔습니다.  
- 업무분담을 실수해서 팀원들을 힘들게 한적도 있었습니다. 하지만 이런 실수, 실패를 통해서 저는 어떻게 하면 좋을까에 대한 고민을 하였습니다.  
일이 생기면 해당 사항에 대해 팀원들의 의견을 경청하고, 업무 분담에 대하여 팀원과의 대화를 통해 타협점을 이끌어 내었습니다.  
- 팀원들과 소통을 하니 팀원들도 왜 이 파트는 이 사람이 해야하는지에 대해 이해할 수 있었습니다.
- 이는 결과적으로 능률의 향상으로 이루어졌고, 다른 팀들에 비해 인원수가 모자랐음에도 좋은 성적을 받은 요소중 하나라고 생각합니다.

4-2. 개발(구현)
- 앱을 개발하는 부분에 있어서 많은 어려움이 있었습니다(인원수, 능력 부족). 그렇기에 더더욱 이곳 저곳 찾아보고, 다른 동기들이 쉴때 더욱 노력하여 다른팀 이상의 결과로 좋은 성적을 받을 수 있었습니다.
- 같이 구현을 맡은 학생이 안드로이드에 대해 알고는 있으나 익숙치 않아 어려움이 많았습니다. 해당 팀원에게 제가 알고 있는 정보들을 최대한 이해하기 쉽게 알려주고 그 결과 해당 팀원은 안드로이드에 대한 지식을 얻을 수 있었고, 저는 자신이 알고 있는 내용을 타인에게 알려줌으로써 다시금 복기할 수 있었고, 더욱 성장할 수 있었습니다.
- 강의에서 배운 내용만으로는 기획과 분석단계에서 만들어낸 내용들을 전부 담을 수 없었습니다. 그렇기에 강의때 다루지 않은 내용(Manifest 권한 등의 세부 사항, 리사이클러 뷰, 프래그먼트, 내부저장소 파일 선택, 미디어 플레이어 등)은 전부 사용법을 찾아서 구현했습니다.
- Firebase를 사용해보고, 이에 대해 여러가지 조사를 진행하면서 안드로이드와 웹서버, DB가 어떻게 통신을 진행하는지 알 수 있었습니다.
