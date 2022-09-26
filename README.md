# kanbanboard
> 작업을 시각화하고, 진행 중인 작업을 제한하며 효율성을 최대화하는 애자일 프로젝트 관리 도구

## 설명
* 작업 시각화 단계는 총 4가지로 한다.
* 첫째로 backlog - 할일(to do)
* 둘째로 in progress - 작업중(doing)
* 셋째로 in test - 테스트중(testing)
* 넷째로 done - 작업 완료
* 엔티티는 users, backLog, doing, testing, done이 있다.

## 설계
* 할일목록을 등록한다.
* 할일 중에서 급하게 처리해야하는 일의 경우 emergency를 일을 올리는 시니어들이 클릭하여 페이징 정렬을 emergency 기준으로 하여 최상단으로 올린다.(시니어들이 볼때 급한 일인경우 emergency를 누르면됨)
* 즉 emergency가 많은 게시글 순으로 정렬되어 표시된다.
* 직원들은 여러개의 할 일중 원하는 일에 들어가서 설명을 보고 do를 클릭한다.
* 그러면 해당 일은 backlog 게시판에서 doing 게시판으로 옮겨진다.
* 옮겨질때 backlog의 id를 받고 해당 id의 필드 값을 doing에 저장하고,
* backlog는 삭제한다.
* doing 게시판은 두가지를 고를 수 있는데, 첫째는 testing 버튼이고, 둘째는 done 버튼이다.
* testing을 클릭할 경우 testing 게시판으로 옮겨진다.
* testing 게시판에서 일 처리가 완료될경우 done을 클릭하여 done 게시판으로 옮긴다. 
* done 게시판은 id순으로 정렬하여서 가장 최신으로 완료된 일 순으로 정렬하여 보여준다.
* 기존의 게시판들은 emergency순으로 정렬하고 그 다음 정렬 기준이 id값이었다.
* 게시판을 버튼 클릭으로 넘어갈때마다(doing 게시판부터) princiapl로 현재 유저와 저장되어있는 worker를 판별하여 둘이 맞을 경우 다음 게시판으로 넘겨준다.
* worker 판별은 서버에서 진행한다.
* 한 게시판에서 다음 게시판으로 넘어갈때 마다 원래 게시판에서는 동시에 삭제 처리해버린다.

## 엔티티 설계
### 1. backLog
* 제목, 내용, 작성자, emergency, startedDate(시작 날짜)
### 2. doing
* 제목, 내용, 작성자, worker, emergency, startedDate(시작 날짜)
### 3. testing
* 제목, 내용, 작성자, worker, emergency, startedDate(시작 날짜)
### 4. done
* 제목, 내용, 작성자, worker, startedDate(시작 날짜), endedDate(끝난 날짜)

## backlog 업데이트 - 여러개의 필드의 조건적 업데이트
* 업데이트시 세가지 경우가 있다.
* 첫째로 제목만 업데이트 하는 경우
* 둘째로 내용만 업데이트 하는 경우
* 셋째로 둘다 업데이트 하는 경우
* 세 경우에 따라 if문으로 분류하고 서비스 로직에서 알맞은 방법에 맞게 업데이트 해준다.

## json body - user는 생략
#### backlog
<pre>
- post -
{
    "title" : "test1",
    "content" : "this is first test"
}

{
  "title" : "test2",
  "content" : "this is second test",
  "emergency" : 2
}

- edit -
{
    "title" : "update test"
}

{
    "content" : "update content"
}

{
    "title" : "update title",
    "content" : "update content"
}
</pre>

## api 설계
#### BackLog
* /backlog - get
* /backlog/post - get/post
* /backlog/{id} - get
* /backlog/emergency/{id} - post
* /backlog/edit/{id} - get/post

#### doing
* /doing - get
* /doing/post/{id} - post, id값은 backlog id