module percupdate

  implicit none

  private
  public :: perc_update

contains

  !
  ! Function to update all entries of the map once.
  !
  ! Should return the actual number of changes, zero if nothing changes.
  !

  subroutine perc_update(n, map, nchange)

    integer, intent(in)                             :: n
    integer, dimension(0:n+1, 0:n+1), intent(inout) :: map
    integer, intent(out)                            :: nchange

    nchange = 0

  end subroutine perc_update

end module percupdate
