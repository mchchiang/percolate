module percinit

  implicit none

  private
  public :: perc_init

contains

  !
  !
  ! Function to initialise the map prior to looking for clusters.
  !
  ! All interior L*L points that are non-zero should be replaced with a
  ! unique positive integer.
  !

  subroutine perc_init(n, percmap)

    integer, intent(in)                             :: n
    integer, dimension(0:n+1, 0:n+1), intent(inout) :: percmap

  end subroutine perc_init

end module percinit
