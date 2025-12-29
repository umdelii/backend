// 登録ボタンsumbit
// submit 中止
// uploadResult liの情報収集した後 form hidden タグにappend

document.querySelector("#create-form").addEventListener("submit", (e) => {
  e.preventDefault();

  const attachInfos = document.querySelectorAll(".uploadResult li");

  let result = "";

  attachInfos.forEach((obj, idx) => {
    result += `<input type="hidden" name="movieImages[${idx}].imgName" value="${obj.dataset.name}">`;
    result += `<input type="hidden" name="movieImages[${idx}].uuid" value="${obj.getAttribute(
      "data-uuid"
    )}">`;
    result += `<input type="hidden" name="movieImages[${idx}].path" value="${obj.dataset.path}">`;
  });

  e.target.insertAdjacentHTML("beforeend", result);

  console.log(e.target.innerHTML);
  e.target.submit();
});
