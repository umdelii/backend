// 削除
document.querySelectorAll(".uploadResult i").forEach((item) => {
  item.addEventListener("click", (e) => {
    e.preventDefault();
    console.log("イベント対象 ", e.target);

    const li = e.target.closest("li");

    if (confirm("この写真を削除しますか？")) {
      li.remove();
    }
  });
});

const deleteBtn = document.querySelector(".delete");
const createForm = document.querySelector("#create-form");
deleteBtn.addEventListener("click", () => {
  createForm.action = "/movie/remove";
  createForm.submit();
});
