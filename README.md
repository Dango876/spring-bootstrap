.antMatchers("/", "/css/**", "/js/**", "/webjars/**").permitAll()- переместитть вниз,
под админа и юзера. От самого защищенного до самого незащищенного надо писать -.

if (roleIds == null || roleIds.isEmpty()) {
roleIds = List.of(2L);
} - убрать бизнес логику в сервисный слой -.

// новая сигнатура  - убрать комментарии -.

redirectAttributes  -  я бы избавился если получится от этого. Очень засоряет код и я не уверен, 
что сильно много логики несет -.

@PreAuthorize("hasAnyRole('USER', 'ADMIN')") - зачем? У тебя же уже есть секьюрность в методе configure. -.

(@AuthenticationPrincipal UserDetails userDetails,
Model model) { - тут явно поместится в одну строку