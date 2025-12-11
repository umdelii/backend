// 검색 폼 submit 시
// submit 중지
// keyword, select 값이 있는지 확인
// 없으면 경고메세지 하나 ㄱ

// page 값을 1로 변경

const form = document.querySelector("#action-form");
form.addEventListener("submit", (e) => {
  e.preventDefault();

  if (form.type.value === "") {
    alert("검색 타입을 선택하세요");
    form.type.focus();
    return;
  } else if (form.keyword.value === "") {
    alert("검색어를 입력하세요");
    form.keyword.focus();
    return;
  }

  form.page.value = 1;
  e.target.submit();
});
