// 삭제 버튼 클릭 시
// submit 기능 중지
// form.action ="가야할 곳"

document.querySelector("#delete-memo").addEventListener("submit", (e) => {
  e.preventDefault();
  const deleteMemo = document.querySelector("#modify-form");
  deleteMemo.action = "/memo/remove";
  e.target.submit();
});
